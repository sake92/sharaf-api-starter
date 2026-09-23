package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class Vet(firstName: String, lastName: String, specialties: Seq[Specialty], id: Option[Int])
object Vet {
  given Configuration = Configuration.default
  given Codec[Vet] = ConfiguredCodec.derived
}
