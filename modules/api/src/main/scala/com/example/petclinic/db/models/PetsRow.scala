package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
object PetsRow {
  inline val tableName = "public.pets"
  inline val id = "id"
  inline val name = "name"
  inline val birthDate = "birth_date"
  inline val typeId = "type_id"
  inline val ownerId = "owner_id"
  inline val allCols = "id, name, birth_date, type_id, owner_id"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".id" + "," + (prefix + ".name") + "," + (prefix + ".birth_date") + "," + (prefix + ".type_id") + "," + (prefix + ".owner_id")
  }
  type PK = Int
}
case class PetsRow(id: Int, name: String, birth_date: LocalDate, type_id: Int, owner_id: Int) derives SqlReadRow {
  def pk: PetsRow.PK = id
}
