package com.example.petclinic

import com.example.petclinic.client.clients.{OwnerClient, PettypesClient}
import com.example.petclinic.client.models.{OwnerFields, PetTypeFields, ProblemDetail}
import io.circe.parser.decode
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

  test("owners can be listed, filtered, and read with their pets and visits") {
    val client = OwnerClient(app.baseUri)
    val listed = client.listOwners(Some("dav")).send(backend)
    assertEquals(listed.code, StatusCode.Ok)
    val owners = listed.body.fold(error => fail(error.getMessage), identity)
    assertEquals(owners.map(_.lastName).toSet, Set("Davis"))
    assertEquals(owners.size, 2)

    val found = client.getOwner(6).send(backend)
    assertEquals(found.code, StatusCode.Ok)
    val owner = found.body.fold(error => fail(error.getMessage), identity)
    assertEquals(owner.firstName, "Jean")
    assertEquals(owner.pets.map(_.name).toSet, Set("Samantha", "Max"))
    assertEquals(owner.pets.flatMap(_.visits).size, 4)
  }

  test("generated owner client creates, updates, reads, and deletes a database row") {
    val client = OwnerClient(app.baseUri)
    val firstName = "Newowner"
    val fields = OwnerFields(firstName, "Example", "1 Main Street", "Madison", "1234567890")

    val createdResponse = client.addOwner(fields).send(backend)
    assertEquals(createdResponse.code, StatusCode.Created)
    val created = createdResponse.body.fold(error => fail(error.getMessage), identity)
    val id = created.id.getOrElse(fail("created owner has no ID"))
    assertEquals(created.pets, Seq.empty)
    assertEquals(ownerNameInDatabase(id), Some(firstName))

    val updatedFields = fields.copy(firstName = "Updated")
    val updatedResponse = client.updateOwner(id, updatedFields).send(backend)
    assertEquals(updatedResponse.code, StatusCode.Ok)
    val updated = updatedResponse.body.fold(error => fail(error.getMessage), identity)
    assertEquals(updated.firstName, "Updated")
    assertEquals(ownerNameInDatabase(id), Some("Updated"))

    val read = client.getOwner(id).send(backend)
    assertEquals(read.body.fold(error => fail(error.getMessage), _.firstName), "Updated")

    val deletedResponse = client.deleteOwner(id).send(backend)
    assertEquals(deletedResponse.code, StatusCode.Ok)
    assertEquals(deletedResponse.body.fold(error => fail(error.getMessage), _.id), Some(id))
    assertEquals(ownerNameInDatabase(id), None)
    assertEquals(client.getOwner(id).send(backend).code, StatusCode.NotFound)
  }

  test("owner errors are structured and deleting an owner with pets is a conflict") {
    val missing = basicRequest.get(uri"${app.baseUri}/owners/${Int.MaxValue}").response(asStringAlways).send(backend)
    assertProblem(missing.code, missing.body, 404)

    val negativeId = basicRequest.get(uri"${app.baseUri}/owners/-1").response(asStringAlways).send(backend)
    assertProblem(negativeId.code, negativeId.body, 400)

    val missingUpdate = basicRequest
      .put(uri"${app.baseUri}/owners/${Int.MaxValue}")
      .contentType("application/json")
      .body("""{"firstName":"Ann","lastName":"Smith","address":"1 Main","city":"Madison","telephone":"1234"}""")
      .response(asStringAlways)
      .send(backend)
    assertProblem(missingUpdate.code, missingUpdate.body, 404)

    val conflict = basicRequest.delete(uri"${app.baseUri}/owners/6").response(asStringAlways).send(backend)
    assertProblem(conflict.code, conflict.body, 409)
    assertEquals(ownerNameInDatabase(6), Some("Jean"))

    val invalid = basicRequest
      .post(uri"${app.baseUri}/owners")
      .contentType("application/json")
      .body("""{"firstName":"Ann","lastName":"Smith","address":"1 Main","city":"Madison","telephone":"abc"}""")
      .response(asStringAlways)
      .send(backend)
    assertEquals(invalid.code, StatusCode.BadRequest)
    val problem = decode[ProblemDetail](invalid.body).fold(error => fail(error.getMessage), identity)
    assertEquals(problem.status, 400)
    assert(problem.schemaValidationErrors.exists(_.message.contains("telephone")))
  }

  private def assertProblem(actualStatus: StatusCode, body: String, expectedStatus: Int): Unit = {
    assertEquals(actualStatus.code, expectedStatus)
    val problem = decode[ProblemDetail](body).fold(error => fail(error.getMessage), identity)
    assertEquals(problem.status, expectedStatus)
    assertEquals(problem.`type`, "about:blank")
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

  private def ownerNameInDatabase(id: Int): Option[String] =
    Using.Manager { use =>
      val connection = use(DriverManager.getConnection(postgres.getJdbcUrl, postgres.getUsername, postgres.getPassword))
      val statement = use(connection.prepareStatement("SELECT first_name FROM owners WHERE id = ?"))
      statement.setInt(1, id)
      val result = use(statement.executeQuery())
      Option.when(result.next())(result.getString("first_name"))
    }.get

  private def availablePort(): Int = {
    val socket = new ServerSocket(0, 0, InetAddress.getLoopbackAddress)
    try socket.getLocalPort
    finally socket.close()
  }
}
