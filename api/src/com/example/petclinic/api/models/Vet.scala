package com.example.petclinic.api.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class Vet(firstName: String, lastName: String, specialties: Seq[Specialty], id: Option[Int]) derives JsonRW
object Vet {
  given Validator[Vet] = Validator
    .derived[Vet]
    .minLength(_.firstName, 1)
    .maxLength(_.firstName, 30)
    .matches(_.firstName, "^[\\p{L}]+([ '-][\\p{L}]+){0,2}$")
    .minLength(_.lastName, 1)
    .maxLength(_.lastName, 30)
    .matches(_.lastName, "^[\\p{L}]+([ '-][\\p{L}]+){0,2}\\.?$")
}
