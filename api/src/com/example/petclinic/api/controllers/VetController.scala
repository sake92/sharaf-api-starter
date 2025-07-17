package com.example.petclinic.api.controllers
import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import com.example.petclinic.api.models.*
class VetController {
  def routes = Routes {
    case GET -> Path("vets") =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Seq[Vet]")
    case POST -> Path("vets") =>
      val reqBody = Request.current.bodyJsonValidated[Vet]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Vet")
    case GET -> Path("vets", param[Int](vetId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Vet")
    case PUT -> Path("vets", param[Int](vetId)) =>
      val reqBody = Request.current.bodyJsonValidated[Vet]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Vet")
    case DELETE -> Path("vets", param[Int](vetId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Vet")
  }
}
