package com.example.petclinic.main

import java.net.URI

private[main] final case class DatabaseConfig(
    jdbcUrl: String,
    user: String,
    password: String,
    poolSize: Int
)

private[main] final case class AppConfig(database: DatabaseConfig, serverHost: String)

private[main] object AppConfig {
  private val PoolSizeVariable = "DB_POOL_SIZE"

  def load(environment: Map[String, String]): AppConfig = {
    val jdbcUrl = required(environment, "JDBC_URL")
    validatePostgresUrl(jdbcUrl)

    val poolSize = required(environment, PoolSizeVariable).toIntOption.getOrElse {
      throw ConfigException(s"$PoolSizeVariable must be an integer between 1 and 100")
    }
    if poolSize < 1 || poolSize > 100 then
      throw ConfigException(s"$PoolSizeVariable must be between 1 and 100, but was $poolSize")

    val serverHost = environment.get("SERVER_HOST").map(_.trim).filter(_.nonEmpty).getOrElse("localhost")
    AppConfig(
      DatabaseConfig(
        jdbcUrl = jdbcUrl,
        user = required(environment, "DB_USER"),
        password = requiredSecret(environment, "DB_PASSWORD"),
        poolSize = poolSize
      ),
      serverHost = serverHost
    )
  }

  private def required(environment: Map[String, String], name: String): String =
    environment.get(name).map(_.trim).filter(_.nonEmpty).getOrElse {
      throw ConfigException(s"Required environment variable $name is missing or empty")
    }

  private def requiredSecret(environment: Map[String, String], name: String): String =
    environment.get(name).filter(_.trim.nonEmpty).getOrElse {
      throw ConfigException(s"Required environment variable $name is missing or empty")
    }

  private def validatePostgresUrl(jdbcUrl: String): Unit = {
    val expectedPrefix = "jdbc:postgresql://"
    if !jdbcUrl.startsWith(expectedPrefix) then
      throw ConfigException("JDBC_URL must be a PostgreSQL JDBC URL starting with jdbc:postgresql://")

    val parsed =
      try URI.create(jdbcUrl.stripPrefix("jdbc:"))
      catch {
        case _: IllegalArgumentException =>
          throw ConfigException("JDBC_URL is not a valid PostgreSQL JDBC URL")
      }
    if Option(parsed.getHost).forall(_.isBlank) || Option(parsed.getPath).forall(_.length <= 1) then
      throw ConfigException("JDBC_URL must include a database host and name")
  }
}

private[main] final case class ConfigException(message: String) extends IllegalArgumentException(message)
