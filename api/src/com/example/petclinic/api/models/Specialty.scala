package com.example.petclinic.api.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class Specialty(id: Int, name: String) derives JsonRW
object Specialty {
  given Validator[Specialty] = Validator.derived[Specialty].min(_.id, 0).minLength(_.name, 1).maxLength(_.name, 80)
}
