package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class Visit(date: Option[LocalDate], description: String, id: Int, petId: Option[Int]) derives JsonRW
object Visit {
  given Validator[Visit] =
    Validator.derived[Visit].minLength(_.description, 1).maxLength(_.description, 255).min(_.id, 0)
}
