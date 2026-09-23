package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
object RolesRow {
  inline val tableName = "public.roles"
  inline val id = "id"
  inline val username = "username"
  inline val role = "role"
  inline val allCols = "id, username, role"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".id" + "," + (prefix + ".username") + "," + (prefix + ".role")
  }
  type PK = Int
}
case class RolesRow(id: Int, username: String, role: String) derives SqlReadRow { def pk: RolesRow.PK = id }
