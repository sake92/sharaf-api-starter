package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class OwnerFields(firstName: String, lastName: String, address: String, city: String, telephone: String)
object OwnerFields {
  given Configuration = Configuration.default
  given Codec[OwnerFields] = ConfiguredCodec.derived
}
