package com.example.petclinic.api.models

import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator

case class PetTypeFields(name: String) derives JsonRW

object PetTypeFields {
  given Validator[PetTypeFields] = Validator
    .derived[PetTypeFields]
    .minLength(_.name, 1)
    .maxLength(_.name, 80)
}
