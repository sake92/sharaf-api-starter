package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import io.circe.{Codec, Json}
import io.circe.derivation.{Configuration, ConfiguredCodec, ConfiguredEnumCodec}
case class ProblemDetail(
    `type`: String,
    title: String,
    status: Int,
    detail: String,
    timestamp: Instant,
    schemaValidationErrors: Seq[ValidationMessage]
)
object ProblemDetail {
  given Configuration = Configuration.default
  given Codec[ProblemDetail] = ConfiguredCodec.derived
}
