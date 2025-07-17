package com.example.petstore.db.models

import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}

object PetsRow {
  inline val tableName = "PUBLIC.PETS"
  inline val id = "ID"
  inline val name = "NAME"
  inline val birthDate = "BIRTH_DATE"
  inline val typeId = "TYPE_ID"
  inline val ownerId = "OWNER_ID"
  inline val allCols = "ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".ID" + "," + (prefix + ".NAME") + "," + (prefix + ".BIRTH_DATE") + "," + (prefix + ".TYPE_ID") + "," + (prefix + ".OWNER_ID")
  }
  type PK = Int
}

case class PetsRow(
    ID: Int,
    NAME: String,
    BIRTH_DATE: LocalDate,
    TYPE_ID: Int,
    OWNER_ID: Int
) derives SqlReadRow {
  def pk: PetsRow.PK = ID
}
