package com.example.petclinic.api.controllers
import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import com.example.petclinic.api.models.*
class OwnerController {
  def routes = Routes {
    case GET -> Path("owners") =>
      case class QP(lastName: Option[String]) derives QueryStringRW
      val qp = Request.current.queryParamsValidated[QP]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Seq[Owner]")
    case POST -> Path("owners") =>
      val reqBody = Request.current.bodyJsonValidated[OwnerFields]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Owner")
    case GET -> Path("owners", param[Int](ownerId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Owner")
    case PUT -> Path("owners", param[Int](ownerId)) =>
      val reqBody = Request.current.bodyJsonValidated[OwnerFields]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Owner")
    case DELETE -> Path("owners", param[Int](ownerId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Owner")
  }
}
