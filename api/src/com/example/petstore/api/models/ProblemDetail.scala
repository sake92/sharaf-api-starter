package com.example.petstore.api.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class ProblemDetail(
    `type`: String,
    title: String,
    status: Int,
    detail: String,
    timestamp: Instant,
    schemaValidationErrors: Seq[ValidationMessage]
) derives JsonRW
object ProblemDetail {
  given Validator[ProblemDetail] = Validator.derived[ProblemDetail].min(_.status, 400).max(_.status, 600)
}
