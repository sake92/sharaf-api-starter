package com.example.petstore.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
object TypesRow {
  inline val tableName = "PUBLIC.TYPES"
  inline val id = "ID"
  inline val name = "NAME"
  inline val allCols = "ID, NAME"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".ID" + "," + (prefix + ".NAME")
  }
  type PK = Int
}
case class TypesRow(ID: Int, NAME: String) derives SqlReadRow { def pk: TypesRow.PK = ID }
