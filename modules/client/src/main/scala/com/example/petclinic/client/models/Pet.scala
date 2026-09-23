package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class Pet(name: String, birthDate: LocalDate, `type`: PetType, id: Int, ownerId: Option[Int], visits: Seq[Visit])
object Pet {
  given Configuration = Configuration.default
  given Codec[Pet] = ConfiguredCodec.derived
}
