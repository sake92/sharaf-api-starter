package com.example.petclinic

import com.example.petclinic.client.clients.PettypesClient
import com.example.petclinic.client.models.PetTypeFields
import com.example.petclinic.main.{AppConfig, DatabaseConfig, PetClinicApplication, RunningPetClinic}
import java.net.{InetAddress, ServerSocket}
import java.sql.DriverManager
import java.util.UUID
import org.flywaydb.core.Flyway
import org.testcontainers.postgresql.PostgreSQLContainer
import scala.util.Using
import sttp.client4.*
import sttp.model.StatusCode

final class PetClinicIntegrationSuite extends munit.FunSuite {
  private val postgres = PostgreSQLContainer("postgres:17.11-alpine")
  private val backend = DefaultSyncBackend()
  private var runningApp = Option.empty[RunningPetClinic]

  override def beforeAll(): Unit = {
    super.beforeAll()
    try {
      postgres.start()
      Flyway
        .configure()
        .dataSource(postgres.getJdbcUrl, postgres.getUsername, postgres.getPassword)
        .locations("classpath:db/migration")
        .load()
        .migrate()

      val config = AppConfig(
        database = DatabaseConfig(
          jdbcUrl = postgres.getJdbcUrl,
          user = postgres.getUsername,
          password = postgres.getPassword,
          poolSize = 4
        ),
        serverHost = "127.0.0.1",
        serverPort = availablePort()
      )
      runningApp = Some(PetClinicApplication.start(config))
    } catch {
      case error: Throwable =>
        backend.close()
        postgres.stop()
        throw error
    }
  }

  override def afterAll(): Unit =
    try runningApp.foreach(_.close())
    finally
      try backend.close()
      finally
        postgres.stop()
        super.afterAll()

  test("the generated client reads migrated seed data and persists a write") {
    val client = PettypesClient(app.baseUri)

    val listed = client.listPetTypes().send(backend)
    assertEquals(listed.code, StatusCode.Ok)
    val petTypes = listed.body.fold(error => fail(error.getMessage), identity)
    assert(petTypes.exists(_.name == "cat"))

    val uniqueName = s"integration-${UUID.randomUUID()}"
    val createdResponse = client.addPetType(PetTypeFields(uniqueName)).send(backend)
    assertEquals(createdResponse.code, StatusCode.Ok)
    val created = createdResponse.body.fold(error => fail(error.getMessage), identity)
    assertEquals(created.name, uniqueName)
    assert(created.id > 0)

    assertEquals(petTypeNameInDatabase(created.id), Some(uniqueName))
  }

  test("the packaged canonical OpenAPI document is served by the API") {
    val response = basicRequest
      .get(uri"${app.baseUri}/openapi.yaml")
      .response(asStringAlways)
      .send(backend)

    assertEquals(response.code, StatusCode.Ok)
    assert(response.body.startsWith("openapi: 3.0.1"))
    assert(response.body.contains("operationId: listPetTypes"))
  }

  test("malformed JSON is rejected at the HTTP boundary") {
    val response = basicRequest
      .post(uri"${app.baseUri}/pettypes")
      .contentType("application/json")
      .body("""{"name":""")
      .response(asStringAlways)
      .send(backend)

    assertEquals(response.code, StatusCode.BadRequest)
  }

  private def app: RunningPetClinic =
    runningApp.getOrElse(fail("integration application did not start"))

  private def petTypeNameInDatabase(id: Int): Option[String] =
    Using.Manager { use =>
      val connection = use(DriverManager.getConnection(postgres.getJdbcUrl, postgres.getUsername, postgres.getPassword))
      val statement = use(connection.prepareStatement("SELECT name FROM types WHERE id = ?"))
      statement.setInt(1, id)
      val result = use(statement.executeQuery())
      Option.when(result.next())(result.getString("name"))
    }.get

  private def availablePort(): Int = {
    val socket = new ServerSocket(0, 0, InetAddress.getLoopbackAddress)
    try socket.getLocalPort
    finally socket.close()
  }
}
