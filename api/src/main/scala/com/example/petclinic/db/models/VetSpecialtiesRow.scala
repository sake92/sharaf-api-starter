package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
object VetSpecialtiesRow {
  inline val tableName = "PUBLIC.VET_SPECIALTIES"
  inline val vetId = "VET_ID"
  inline val specialtyId = "SPECIALTY_ID"
  inline val allCols = "VET_ID, SPECIALTY_ID"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".VET_ID" + "," + (prefix + ".SPECIALTY_ID")
  }
  case class PK()
}
case class VetSpecialtiesRow(VET_ID: Int, SPECIALTY_ID: Int) derives SqlReadRow {
  def pk: VetSpecialtiesRow.PK = VetSpecialtiesRow.PK()
}
