package com.example.petclinic.api.controllers

import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import com.example.petclinic.api.models.*

class UserController {
  def routes = Routes {
    case POST -> Path("users") =>
      val reqBody = Request.current.bodyJsonValidated[User]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return User")
    case PUT -> Path("users", username) =>
      val reqBody = Request.current.bodyJsonValidated[User]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return updated User")
  }
}
