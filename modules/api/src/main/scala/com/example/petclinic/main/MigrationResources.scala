package com.example.petclinic.main

import java.io.{InputStreamReader, Reader}
import java.nio.charset.StandardCharsets
import scala.jdk.CollectionConverters.*
import scala.util.Using
import org.flywaydb.core.api.ResourceProvider
import org.flywaydb.core.api.resource.LoadableResource

/** Explicit resource discovery also works when Native Image cannot scan classpath directories. */
private[main] object MigrationResources extends ResourceProvider {
  private val directory = "db/migration/"
  private val loader = getClass.getClassLoader

  private def open(path: String) =
    Option(loader.getResourceAsStream(path))
      .getOrElse(throw IllegalStateException(s"Missing migration resource: $path"))

  private lazy val names = {
    val migrations = Using.resource(scala.io.Source.fromInputStream(open("db/migration-index.txt"), "UTF-8")) {
      _.getLines().filter(_.nonEmpty).toList
    }
    require(migrations.nonEmpty, "No database migrations were packaged")
    migrations
  }

  override def getResource(name: String): LoadableResource = {
    val filename = name.stripPrefix(directory)
    if names.contains(filename) then MigrationResource(filename) else null
  }

  override def getResources(prefix: String, suffixes: Array[String]): java.util.Collection[LoadableResource] =
    names
      .filter(name => name.startsWith(prefix) && suffixes.exists(name.endsWith))
      .map(name => MigrationResource(name): LoadableResource)
      .asJava

  private final class MigrationResource(name: String) extends LoadableResource {
    private val path = directory + name
    override def read(): Reader = InputStreamReader(open(path), StandardCharsets.UTF_8)
    override def getFilename(): String = name
    override def getRelativePath(): String = name
    override def getAbsolutePath(): String = path
    override def getAbsolutePathOnDisk(): String = path
  }
}
