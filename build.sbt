import ba.sake.openapi4s.OpenApi4sPlugin
import ba.sake.openapi4s.OpenApi4sPlugin.autoImport.*
import ba.sake.sbt.squery.SqueryPlugin.autoImport.*

lazy val jdbcUrl = settingKey[String]("JDBC URL used by the application and code generators")

lazy val api = project
  .in(file("api"))
  .enablePlugins(FlywayPlugin, OpenApi4sPlugin, ba.sake.sbt.squery.SqueryPlugin)
  .settings(
    name := "sharaf-api-starter",
    scalaVersion := "3.7.1",
    libraryDependencies ++= Seq(
      "ba.sake" %% "sharaf-undertow" % "0.18.0",
      "ba.sake" %% "squery" % "0.12.0",
      "com.h2database" % "h2" % "2.3.232",
      "org.webjars" % "swagger-ui" % "5.20.1"
    ),
    jdbcUrl := s"jdbc:h2:file:${(baseDirectory.value / "h2_db").getAbsolutePath}",
    Compile / run / fork := true,
    Compile / run / envVars := Map("JDBC_URL" -> jdbcUrl.value),
    flywayUrl := jdbcUrl.value,
    flywayLocations := Seq(s"filesystem:${((Compile / resourceDirectory).value / "db" / "migration").getAbsolutePath}"),
    squeryJdbcUrl := jdbcUrl.value,
    squerySchemaMappings := Seq("PUBLIC" -> "com.example.petclinic.db"),
    squeryJdbcDeps := Seq("com.h2database" % "h2" % "2.3.232"),
    squeryVersion := "0.12.0",
    openApi4sPackage := "com.example.petclinic.api",
    openApi4sFile := (Compile / resourceDirectory).value / "public" / "openapi.yaml",
    openApi4sVersion := "0.9.0"
  )
