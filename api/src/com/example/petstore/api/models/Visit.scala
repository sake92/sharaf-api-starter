package com.example.petstore.api.models

import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
import com.example.petstore.db.models.*

case class Visit(date: Option[LocalDate], description: String, id: Int, petId: Option[Int]) derives JsonRW

object Visit {
  given Validator[Visit] =
    Validator.derived[Visit].minLength(_.description, 1).maxLength(_.description, 255).min(_.id, 0)

  def fromRow(row: VisitsRow): Visit =
    Visit(
      date = Option(row.VISIT_DATE),
      description = row.DESCRIPTION,
      id = row.ID,
      petId = Option(row.PET_ID)
    )
}
