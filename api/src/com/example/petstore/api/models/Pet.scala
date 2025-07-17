package com.example.petstore.api.models

import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
import com.example.petstore.db.models.*

case class Pet(
    name: String,
    birthDate: LocalDate,
    `type`: PetType,
    id: Int,
    ownerId: Option[Int],
    visits: Seq[Visit]
) derives JsonRW

object Pet {
  given Validator[Pet] = Validator.derived[Pet].maxLength(_.name, 30).min(_.id, 0)

  def fromRow(row: PetsRow, petType: TypesRow, visits: Seq[VisitsRow]): Pet =
    Pet(
      name = row.NAME,
      birthDate = row.BIRTH_DATE,
      `type` = PetType.fromRow(petType),
      id = row.ID,
      ownerId = Some(row.OWNER_ID),
      visits = visits.map(Visit.fromRow)
    )
}
