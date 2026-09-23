package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class Role(name: String)
object Role {
  given Configuration = Configuration.default
  given Codec[Role] = ConfiguredCodec.derived
}
