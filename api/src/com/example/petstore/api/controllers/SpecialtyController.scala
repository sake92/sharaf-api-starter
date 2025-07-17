package com.example.petstore.api.controllers
import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import com.example.petstore.api.models.*
class SpecialtyController {
  def routes = Routes {
    case GET -> Path("specialties") =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Seq[Specialty]")
    case POST -> Path("specialties") =>
      val reqBody = Request.current.bodyJsonValidated[Specialty]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Specialty")
    case GET -> Path("specialties", param[Int](specialtyId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Specialty")
    case PUT -> Path("specialties", param[Int](specialtyId)) =>
      val reqBody = Request.current.bodyJsonValidated[Specialty]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Specialty")
    case DELETE -> Path("specialties", param[Int](specialtyId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Specialty")
  }
}
