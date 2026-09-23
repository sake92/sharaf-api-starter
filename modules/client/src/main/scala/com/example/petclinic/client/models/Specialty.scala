package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class Specialty(id: Int, name: String)
object Specialty {
  given Configuration = Configuration.default
  given Codec[Specialty] = ConfiguredCodec.derived
}
