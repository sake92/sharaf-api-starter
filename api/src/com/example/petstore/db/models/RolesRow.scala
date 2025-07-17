package com.example.petstore.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
object RolesRow {
  inline val tableName = "PUBLIC.ROLES"
  inline val id = "ID"
  inline val username = "USERNAME"
  inline val role = "ROLE"
  inline val allCols = "ID, USERNAME, ROLE"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".ID" + "," + (prefix + ".USERNAME") + "," + (prefix + ".ROLE")
  }
  type PK = Int
}
case class RolesRow(ID: Int, USERNAME: String, ROLE: String) derives SqlReadRow { def pk: RolesRow.PK = ID }
