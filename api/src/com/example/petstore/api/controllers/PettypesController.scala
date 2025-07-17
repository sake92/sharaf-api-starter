package com.example.petstore.api.controllers
import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import com.example.petstore.api.models.*
class PettypesController {
  def routes = Routes {
    case GET -> Path("pettypes") =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Seq[PetType]")
    case POST -> Path("pettypes") =>
      val reqBody = Request.current.bodyJsonValidated[PetTypeFields]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return PetType")
    case GET -> Path("pettypes", param[Int](petTypeId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return PetType")
    case PUT -> Path("pettypes", param[Int](petTypeId)) =>
      val reqBody = Request.current.bodyJsonValidated[PetType]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return PetType")
    case DELETE -> Path("pettypes", param[Int](petTypeId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return PetType")
  }
}
