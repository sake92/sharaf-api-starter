package com.example.petclinic

import com.typesafe.config.{ConfigFactory, ConfigResolveOptions}
import scala.jdk.CollectionConverters.*

final class ServerPortSuite extends munit.FunSuite {
  test("server port uses SERVER_PORT, then PORT, then the local default") {
    val cases = Seq(
      Map.empty[String, String] -> 8080,
      Map("PORT" -> "10000") -> 10000,
      Map("SERVER_PORT" -> "9000") -> 9000,
      Map("PORT" -> "10000", "SERVER_PORT" -> "9000") -> 9000
    )
    cases.foreach { (variables, expected) =>
      val config = ConfigFactory
        .parseResources("reference.conf")
        .withFallback(ConfigFactory.parseMap(variables.asJava))
        .resolve(ConfigResolveOptions.defaults().setUseSystemEnvironment(false))
      assertEquals(config.getInt("petclinic.serverPort"), expected)
    }
  }
}
