package com.example.petstore.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
object VetsRow {
  inline val tableName = "PUBLIC.VETS"
  inline val id = "ID"
  inline val firstName = "FIRST_NAME"
  inline val lastName = "LAST_NAME"
  inline val allCols = "ID, FIRST_NAME, LAST_NAME"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".ID" + "," + (prefix + ".FIRST_NAME") + "," + (prefix + ".LAST_NAME")
  }
  type PK = Int
}
case class VetsRow(ID: Int, FIRST_NAME: String, LAST_NAME: String) derives SqlReadRow { def pk: VetsRow.PK = ID }
