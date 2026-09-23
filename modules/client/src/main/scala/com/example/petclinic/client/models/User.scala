package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class User(username: String, password: Option[String], enabled: Option[Boolean], roles: Option[Seq[Role]])
object User {
  given Configuration = Configuration.default
  given Codec[User] = ConfiguredCodec.derived
}
