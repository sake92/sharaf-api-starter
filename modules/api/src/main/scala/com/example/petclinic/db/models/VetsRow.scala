package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
object VetsRow {
  inline val tableName = "public.vets"
  inline val id = "id"
  inline val firstName = "first_name"
  inline val lastName = "last_name"
  inline val allCols = "id, first_name, last_name"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".id" + "," + (prefix + ".first_name") + "," + (prefix + ".last_name")
  }
  type PK = Int
}
case class VetsRow(id: Int, first_name: String, last_name: String) derives SqlReadRow { def pk: VetsRow.PK = id }
