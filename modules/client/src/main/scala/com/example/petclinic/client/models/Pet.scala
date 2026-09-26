package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class Pet(name: String, birthDate: LocalDate, `type`: PetType, id: Int, ownerId: Option[Int], visits: Seq[Visit])
    derives JsonRW
object Pet { given Validator[Pet] = Validator.derived[Pet].maxLength(_.name, 30).min(_.id, 0) }
