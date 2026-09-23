package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class PetType(name: String, id: Int)
object PetType {
  given Configuration = Configuration.default
  given Codec[PetType] = ConfiguredCodec.derived
}
