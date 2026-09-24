package com.example.petclinic.api.controllers

import ba.sake.sharaf.Response
import com.example.petclinic.api.models.{ProblemDetail, ValidationMessage}
import java.time.Instant
import sttp.model.StatusCode

object ApiProblem {
  def response(status: StatusCode, detail: String, errors: Seq[ValidationMessage] = Seq.empty) = {
    val title = status match {
      case StatusCode.BadRequest => "Bad Request"
      case StatusCode.NotFound   => "Not Found"
      case StatusCode.Conflict   => "Conflict"
      case _                     => "Server Error"
    }
    Response
      .withStatus(status)
      .withBody(
        ProblemDetail("about:blank", title, status.code, detail, Instant.now(), errors)
      )
  }
}
