package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
object VetSpecialtiesRow {
  inline val tableName = "public.vet_specialties"
  inline val vetId = "vet_id"
  inline val specialtyId = "specialty_id"
  inline val allCols = "vet_id, specialty_id"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".vet_id" + "," + (prefix + ".specialty_id")
  }
  case class PK()
}
case class VetSpecialtiesRow(vet_id: Int, specialty_id: Int) derives SqlReadRow {
  def pk: VetSpecialtiesRow.PK = VetSpecialtiesRow.PK()
}
