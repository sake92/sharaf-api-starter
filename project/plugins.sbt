addSbtPlugin("ba.sake" % "sbt-openapi4s" % "0.1.0")
addSbtPlugin("ba.sake" % "sbt-squery" % "0.1.0")
addSbtPlugin("com.github.sbt" % "flyway-sbt" % "11.11.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.6.2")
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "1.0.0")

libraryDependencies ++= Seq(
  "org.flywaydb" % "flyway-database-postgresql" % "11.11.0",
  "org.postgresql" % "postgresql" % "42.7.13"
)
