package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class Owner(
    firstName: String,
    lastName: String,
    address: String,
    city: String,
    telephone: String,
    id: Option[Int],
    pets: Seq[Pet]
)
object Owner {
  given Configuration = Configuration.default
  given Codec[Owner] = ConfiguredCodec.derived
}
