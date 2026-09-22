package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
object FlywaySchemaHistoryRow {
  inline val tableName = "PUBLIC.flyway_schema_history"
  inline val installedRank = "installed_rank"
  inline val version = "version"
  inline val description = "description"
  inline val `type` = "type"
  inline val script = "script"
  inline val checksum = "checksum"
  inline val installedBy = "installed_by"
  inline val installedOn = "installed_on"
  inline val executionTime = "execution_time"
  inline val success = "success"
  inline val allCols =
    "installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".installed_rank" + "," + (prefix + ".version") + "," + (prefix + ".description") + "," + (prefix + ".type") + "," + (prefix + ".script") + "," + (prefix + ".checksum") + "," + (prefix + ".installed_by") + "," + (prefix + ".installed_on") + "," + (prefix + ".execution_time") + "," + (prefix + ".success")
  }
  type PK = Int
}
case class FlywaySchemaHistoryRow(
    installed_rank: Int,
    version: Option[String],
    description: String,
    `type`: String,
    script: String,
    checksum: Option[Int],
    installed_by: String,
    installed_on: LocalDateTime,
    execution_time: Int,
    success: Boolean
) derives SqlReadRow { def pk: FlywaySchemaHistoryRow.PK = installed_rank }
