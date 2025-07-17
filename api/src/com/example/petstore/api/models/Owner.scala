package com.example.petstore.api.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class Owner(
    firstName: String,
    lastName: String,
    address: String,
    city: String,
    telephone: String,
    id: Option[Int],
    pets: Seq[Pet]
) derives JsonRW
object Owner {
  given Validator[Owner] = Validator
    .derived[Owner]
    .minLength(_.firstName, 1)
    .maxLength(_.firstName, 30)
    .matches(_.firstName, "^[\\p{L}]+([ '-][\\p{L}]+){0,2}$")
    .minLength(_.lastName, 1)
    .maxLength(_.lastName, 30)
    .matches(_.lastName, "^[\\p{L}]+([ '-][\\p{L}]+){0,2}\\.?$")
    .minLength(_.address, 1)
    .maxLength(_.address, 255)
    .minLength(_.city, 1)
    .maxLength(_.city, 80)
    .minLength(_.telephone, 1)
    .maxLength(_.telephone, 20)
    .matches(_.telephone, "^[0-9]*$")
}
