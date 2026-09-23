package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
object VisitsRow {
  inline val tableName = "public.visits"
  inline val id = "id"
  inline val petId = "pet_id"
  inline val visitDate = "visit_date"
  inline val description = "description"
  inline val allCols = "id, pet_id, visit_date, description"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".id" + "," + (prefix + ".pet_id") + "," + (prefix + ".visit_date") + "," + (prefix + ".description")
  }
  type PK = Int
}
case class VisitsRow(id: Int, pet_id: Int, visit_date: LocalDate, description: String) derives SqlReadRow {
  def pk: VisitsRow.PK = id
}
