package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class Visit(date: Option[LocalDate], description: String, id: Int, petId: Option[Int])
object Visit {
  given Configuration = Configuration.default
  given Codec[Visit] = ConfiguredCodec.derived
}
