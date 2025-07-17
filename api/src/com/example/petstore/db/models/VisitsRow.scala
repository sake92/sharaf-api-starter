package com.example.petstore.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
object VisitsRow {
  inline val tableName = "PUBLIC.VISITS"
  inline val id = "ID"
  inline val petId = "PET_ID"
  inline val visitDate = "VISIT_DATE"
  inline val description = "DESCRIPTION"
  inline val allCols = "ID, PET_ID, VISIT_DATE, DESCRIPTION"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".ID" + "," + (prefix + ".PET_ID") + "," + (prefix + ".VISIT_DATE") + "," + (prefix + ".DESCRIPTION")
  }
  type PK = Int
}
case class VisitsRow(ID: Int, PET_ID: Int, VISIT_DATE: LocalDate, DESCRIPTION: String) derives SqlReadRow {
  def pk: VisitsRow.PK = ID
}
