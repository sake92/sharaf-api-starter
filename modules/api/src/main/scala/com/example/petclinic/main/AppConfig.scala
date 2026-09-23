package com.example.petclinic.main

import ba.sake.tupson.{JsonRW, given}
import ba.sake.tupson.config.*
import ba.sake.validson.*
import com.typesafe.config.ConfigFactory
import java.net.URI
import scala.util.control.NonFatal

final case class DatabaseConfig(
    jdbcUrl: String,
    user: String,
    password: String,
    poolSize: Int
) derives JsonRW

object DatabaseConfig {
  given Validator[DatabaseConfig] = Validator
    .derived[DatabaseConfig]
    .notBlank(_.jdbcUrl)
    .and(_.jdbcUrl, isPostgresJdbcUrl, "must be a PostgreSQL JDBC URL with a host and database name")
    .notBlank(_.user)
    .notBlank(_.password)
    .between(_.poolSize, 1, 100)

  private def isPostgresJdbcUrl(value: String): Boolean =
    value.startsWith("jdbc:postgresql://") &&
      (try {
        val uri = URI.create(value.stripPrefix("jdbc:"))
        Option(uri.getHost).exists(!_.isBlank) && Option(uri.getPath).exists(_.length > 1)
      } catch {
        case _: IllegalArgumentException => false
      })
}

final case class AppConfig(database: DatabaseConfig, serverHost: String, serverPort: Int) derives JsonRW

object AppConfig {
  given Validator[AppConfig] = Validator
    .derived[AppConfig]
    .notBlank(_.serverHost)
    .between(_.serverPort, 1, 65535)

  def load(): AppConfig =
    try ConfigFactory.load().getConfig("petclinic").parseConfig[AppConfig].validateOrThrow
    catch {
      case error: ValidsonException =>
        val message = error.errors
          .map(error => s"petclinic${error.path.stripPrefix("$")}: ${error.msg}")
          .mkString("; ")
        throw ConfigException(message)
      case NonFatal(error) =>
        throw ConfigException(Option(error.getMessage).getOrElse(error.getClass.getSimpleName))
    }
}

private[main] final case class ConfigException(message: String) extends IllegalArgumentException(message)
