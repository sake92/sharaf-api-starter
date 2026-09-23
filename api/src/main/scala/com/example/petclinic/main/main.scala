package com.example.petclinic.main

import ba.sake.squery.SqueryContext
import ba.sake.sharaf.*
import ba.sake.sharaf.undertow.UndertowSharafServer
import com.example.petclinic.api.controllers.*
import com.example.petclinic.ui.controllers.SwaggerUIController
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import java.util.concurrent.atomic.AtomicBoolean

@main def apiMain: Unit = {
  System.err.println("petclinic-api: starting")
  val config =
    try AppConfig.load()
    catch {
      case error: ConfigException =>
        System.err.println(s"petclinic-api: configuration error: ${error.getMessage}")
        sys.exit(2)
    }

  val dataSource = createDataSource(config.database)
  val server =
    try {
      val dbCtx = SqueryContext(dataSource)
      val routes = Routes.merge(
        Seq(
          FailingController().routes,
          OwnerController().routes,
          PetController(dbCtx).routes,
          PettypesController(dbCtx).routes,
          SpecialtyController().routes,
          UserController().routes,
          VetController().routes,
          VisitController().routes,
          SwaggerUIController().routes
        )
      )
      UndertowSharafServer(config.serverHost, 8080, routes)
    } catch {
      case error: Throwable =>
        dataSource.close()
        throw error
    }

  val stopped = AtomicBoolean(false)
  def stop(): Unit =
    if stopped.compareAndSet(false, true) then {
      System.err.println("petclinic-api: shutting down")
      try server.stop()
      finally dataSource.close()
    }

  Runtime.getRuntime.addShutdownHook(Thread(() => stop(), "petclinic-api-shutdown"))
  try {
    server.start()
    System.err.println(s"petclinic-api: listening on http://${config.serverHost}:8080")
  } catch {
    case error: Throwable =>
      stop()
      throw error
  }
}

private def createDataSource(config: DatabaseConfig): HikariDataSource = {
  val hikari = HikariConfig()
  hikari.setJdbcUrl(config.jdbcUrl)
  hikari.setUsername(config.user)
  hikari.setPassword(config.password)
  hikari.setMaximumPoolSize(config.poolSize)
  hikari.setMinimumIdle(math.min(2, config.poolSize))
  hikari.setConnectionTimeout(5000)
  hikari.setValidationTimeout(3000)
  hikari.setInitializationFailTimeout(1)
  hikari.setPoolName("petclinic-db")
  hikari.addDataSourceProperty("tcpKeepAlive", "true")
  HikariDataSource(hikari)
}
