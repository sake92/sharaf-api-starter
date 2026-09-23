import ba.sake.openapi4s.OpenApi4sPlugin
import ba.sake.openapi4s.OpenApi4sPlugin.autoImport.*
import ba.sake.sbt.squery.SqueryPlugin.autoImport.*

lazy val jdbcUrl = settingKey[String]("JDBC URL used by the application and code generators")
lazy val dbUser = settingKey[String]("Database user used by Flyway and code generators")
lazy val dbPassword = settingKey[String]("Database password used by Flyway and code generators")

lazy val api = project
  .in(file("api"))
  .enablePlugins(FlywayPlugin, OpenApi4sPlugin, ba.sake.sbt.squery.SqueryPlugin, PackPlugin)
  .settings(
    name := "sharaf-api-starter",
    scalaVersion := "3.7.4",
    libraryDependencies ++= Seq(
      "ba.sake" %% "sharaf-undertow" % "0.18.0",
      "ba.sake" %% "squery" % "0.12.0",
      "com.zaxxer" % "HikariCP" % "7.1.0",
      "org.postgresql" % "postgresql" % "42.7.13",
      "org.webjars" % "swagger-ui" % "5.20.1"
    ),
    jdbcUrl := "jdbc:postgresql://localhost:5432/petclinic",
    dbUser := "petclinic",
    dbPassword := "petclinic",
    Compile / run / fork := true,
    Compile / run / envVars := Map(
      "JDBC_URL" -> jdbcUrl.value,
      "DB_USER" -> dbUser.value,
      "DB_PASSWORD" -> dbPassword.value,
      "DB_POOL_SIZE" -> "10"
    ),
    flywayUrl := jdbcUrl.value,
    flywayUser := dbUser.value,
    flywayPassword := dbPassword.value,
    flywayLocations := Seq(s"filesystem:${((Compile / resourceDirectory).value / "db" / "migration").getAbsolutePath}"),
    squeryJdbcUrl := s"${jdbcUrl.value}?user=${dbUser.value}&password=${dbPassword.value}",
    squerySchemaMappings := Seq("public" -> "com.example.petclinic.db"),
    squeryJdbcDeps := Seq("org.postgresql" % "postgresql" % "42.7.13"),
    squeryVersion := "0.12.0",
    openApi4sPackage := "com.example.petclinic.api",
    openApi4sFile := (Compile / resourceDirectory).value / "public" / "openapi.yaml",
    openApi4sVersion := "0.9.0",
    packMain := Map("petclinic-api" -> "com.example.petclinic.main.apiMain")
  )
