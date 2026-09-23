package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
object OwnersRow {
  inline val tableName = "public.owners"
  inline val id = "id"
  inline val firstName = "first_name"
  inline val lastName = "last_name"
  inline val address = "address"
  inline val city = "city"
  inline val telephone = "telephone"
  inline val allCols = "id, first_name, last_name, address, city, telephone"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".id" + "," + (prefix + ".first_name") + "," + (prefix + ".last_name") + "," + (prefix + ".address") + "," + (prefix + ".city") + "," + (prefix + ".telephone")
  }
  type PK = Int
}
case class OwnersRow(id: Int, first_name: String, last_name: String, address: String, city: String, telephone: String)
    derives SqlReadRow { def pk: OwnersRow.PK = id }
