package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class ValidationMessage(message: String)
object ValidationMessage {
  given Configuration = Configuration.default
  given Codec[ValidationMessage] = ConfiguredCodec.derived
}
