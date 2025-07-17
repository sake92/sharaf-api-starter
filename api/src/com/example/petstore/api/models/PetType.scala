package com.example.petstore.api.models

import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
import com.example.petstore.db.models.*

case class PetType(name: String, id: Int) derives JsonRW

object PetType {
  given Validator[PetType] = Validator.derived[PetType].minLength(_.name, 1).maxLength(_.name, 80).min(_.id, 0)

  def fromRow(row: TypesRow): PetType =
    PetType(
      name = row.NAME,
      id = row.ID
    )
}
