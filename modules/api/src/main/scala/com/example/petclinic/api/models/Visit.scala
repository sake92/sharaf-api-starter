package com.example.petclinic.api.models

import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
import com.example.petclinic.db.models.*

case class Visit(date: Option[LocalDate], description: String, id: Int, petId: Option[Int]) derives JsonRW

object Visit {
  given Validator[Visit] =
    Validator.derived[Visit].minLength(_.description, 1).maxLength(_.description, 255).min(_.id, 0)

  def fromRow(row: VisitsRow): Visit =
    Visit(
      date = Option(row.visit_date),
      description = row.description,
      id = row.id,
      petId = Option(row.pet_id)
    )
}
