package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class VetFields(firstName: String, lastName: String, specialties: Seq[Specialty])
object VetFields {
  given Configuration = Configuration.default
  given Codec[VetFields] = ConfiguredCodec.derived
}
