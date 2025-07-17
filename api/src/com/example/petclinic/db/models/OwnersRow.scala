package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
object OwnersRow {
  inline val tableName = "PUBLIC.OWNERS"
  inline val id = "ID"
  inline val firstName = "FIRST_NAME"
  inline val lastName = "LAST_NAME"
  inline val address = "ADDRESS"
  inline val city = "CITY"
  inline val telephone = "TELEPHONE"
  inline val allCols = "ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".ID" + "," + (prefix + ".FIRST_NAME") + "," + (prefix + ".LAST_NAME") + "," + (prefix + ".ADDRESS") + "," + (prefix + ".CITY") + "," + (prefix + ".TELEPHONE")
  }
  type PK = Int
}
case class OwnersRow(ID: Int, FIRST_NAME: String, LAST_NAME: String, ADDRESS: String, CITY: String, TELEPHONE: String)
    derives SqlReadRow { def pk: OwnersRow.PK = ID }
