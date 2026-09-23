package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class VisitFields(date: Option[LocalDate], description: String)
object VisitFields {
  given Configuration = Configuration.default
  given Codec[VisitFields] = ConfiguredCodec.derived
}
