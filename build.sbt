import ba.sake.openapi4s.OpenApi4sPlugin
import ba.sake.openapi4s.OpenApi4sPlugin.autoImport.*
import ba.sake.sbt.squery.SqueryPlugin.autoImport.*

lazy val jdbcUrl = settingKey[String]("JDBC URL used by the application and code generators")
lazy val dbUser = settingKey[String]("Database user used by Flyway and code generators")
lazy val dbPassword = settingKey[String]("Database password used by Flyway and code generators")
lazy val generateContracts = taskKey[Unit]("Generate server and client code from the canonical OpenAPI document")

lazy val scala3Version = "3.9.0"
lazy val openApi4sGeneratorVersion = "0.9.0"
lazy val flywayVersion = "11.11.0"
lazy val canonicalOpenApiFile = file("openapi/petclinic.yaml")

lazy val root = project
  .in(file("."))
  .aggregate(api, client, integrationTests)
  .settings(
    name := "sharaf-api-starter-root",
    publish / skip := true,
    generateContracts := {
      (api / openApi4sGenerate).value
      (client / openApi4sGenerate).value
    }
  )

lazy val api = project
  .in(file("modules/api"))
  .enablePlugins(FlywayPlugin, OpenApi4sPlugin, ba.sake.sbt.squery.SqueryPlugin, PackPlugin)
  .settings(
    name := "sharaf-api-starter",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "ba.sake" %% "sharaf-undertow" % "0.19.0",
      "ba.sake" %% "squery" % "0.12.0",
      "com.zaxxer" % "HikariCP" % "7.1.0",
      "org.flywaydb" % "flyway-core" % flywayVersion,
      "org.flywaydb" % "flyway-database-postgresql" % flywayVersion,
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
    openApi4sFile := canonicalOpenApiFile,
    openApi4sVersion := openApi4sGeneratorVersion,
    Compile / resourceGenerators += Def.task {
      val target = (Compile / resourceManaged).value / "public" / "openapi.yaml"
      IO.copyFile(canonicalOpenApiFile, target)
      Seq(target)
    }.taskValue,
    packMain := Map("petclinic-api" -> "com.example.petclinic.main.apiMain")
  )

lazy val client = project
  .in(file("modules/client"))
  .enablePlugins(OpenApi4sPlugin)
  .settings(
    name := "petclinic-client",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "ba.sake" %% "tupson-sttp" % "0.30.0",
      "ba.sake" %% "validson" % "0.19.0"
    ),
    openApi4sModels := "tupson",
    openApi4sFramework := None,
    openApi4sClient := Some("sttp"),
    openApi4sPackage := "com.example.petclinic.client",
    openApi4sFile := canonicalOpenApiFile,
    openApi4sVersion := openApi4sGeneratorVersion
  )

lazy val integrationTests = project
  .in(file("modules/integration-tests"))
  .dependsOn(api, client)
  .settings(
    name := "petclinic-integration-tests",
    scalaVersion := scala3Version,
    publish / skip := true,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.3.4" % Test,
      "org.testcontainers" % "testcontainers-postgresql" % "2.0.3" % Test
    ),
    Test / fork := true,
    Test / parallelExecution := false
  )
