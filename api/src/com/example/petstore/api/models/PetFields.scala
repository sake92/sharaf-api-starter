package com.example.petstore.api.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class PetFields(name: String, birthDate: LocalDate, `type`: PetType) derives JsonRW
object PetFields { given Validator[PetFields] = Validator.derived[PetFields].maxLength(_.name, 30) }
