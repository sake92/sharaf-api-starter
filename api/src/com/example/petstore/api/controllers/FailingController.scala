package com.example.petstore.api.controllers
import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import com.example.petstore.api.models.*
class FailingController {
  def routes = Routes { case GET -> Path("oops") =>
    Response.withStatus(StatusCode.NotImplemented)
  }
}
