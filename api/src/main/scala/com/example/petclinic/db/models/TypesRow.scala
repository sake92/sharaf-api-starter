package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
object TypesRow {
  inline val tableName = "public.types"
  inline val id = "id"
  inline val name = "name"
  inline val allCols = "id, name"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".id" + "," + (prefix + ".name")
  }
  type PK = Int
}
case class TypesRow(id: Int, name: String) derives SqlReadRow { def pk: TypesRow.PK = id }
