package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class PetFields(name: String, birthDate: LocalDate, `type`: PetType)
object PetFields {
  given Configuration = Configuration.default
  given Codec[PetFields] = ConfiguredCodec.derived
}
