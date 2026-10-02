package com.example.petclinic.api.controllers
import sttp.model.StatusCode
import ba.sake.sharaf.*, routing.*
import javax.sql.DataSource
import scala.util.Using
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import com.example.petclinic.api.models._
import java.time._
import java.util.UUID

class HealthController(dataSource: DataSource) {
  def routes = Routes {
    case GET -> Path("health", "live") =>
      Response.withStatus(StatusCode.Ok)
    case GET -> Path("health", "ready") =>
      val ready = Using(dataSource.getConnection)(_.isValid(1)).getOrElse(false)
      if ready then Response.withStatus(StatusCode.Ok)
      else ApiProblem.response(StatusCode.ServiceUnavailable, "Database is unavailable")
  }
}
