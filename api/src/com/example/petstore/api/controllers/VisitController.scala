package com.example.petstore.api.controllers
import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import com.example.petstore.api.models.*
class VisitController {
  def routes = Routes {
    case POST -> Path("owners", param[Int](ownerId), "pets", param[Int](petId), "visits") =>
      val reqBody = Request.current.bodyJsonValidated[VisitFields]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Visit")
    case GET -> Path("visits") =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Seq[Visit]")
    case POST -> Path("visits") =>
      val reqBody = Request.current.bodyJsonValidated[Visit]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Visit")
    case GET -> Path("visits", param[Int](visitId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Visit")
    case PUT -> Path("visits", param[Int](visitId)) =>
      val reqBody = Request.current.bodyJsonValidated[Visit]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Visit")
    case DELETE -> Path("visits", param[Int](visitId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Visit")
  }
}
