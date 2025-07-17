package com.example.petclinic.api.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class VisitFields(date: Option[LocalDate], description: String) derives JsonRW
object VisitFields {
  given Validator[VisitFields] =
    Validator.derived[VisitFields].minLength(_.description, 1).maxLength(_.description, 255)
}
