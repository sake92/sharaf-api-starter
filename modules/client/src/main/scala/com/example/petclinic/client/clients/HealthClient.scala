package com.example.petclinic.client.clients
import sttp.client4.*
import ba.sake.tupson.{given, *}
import ba.sake.sttp.tupson.*
import com.example.petclinic.client.models.*
object HealthClient { val server1: String = "/" }
class HealthClient(baseUrl: String) {
  def liveness(): Request[Either[ResponseException[String], Unit]] = {
    basicRequest
      .get(uri"$baseUrl/health/live")
      .response(asString.mapWithMetadata(ResponseAs.deserializeRightCatchingExceptions(_ => ())))
  }
  def readiness(): Request[Either[ResponseException[String], Unit]] = {
    basicRequest
      .get(uri"$baseUrl/health/ready")
      .response(asString.mapWithMetadata(ResponseAs.deserializeRightCatchingExceptions(_ => ())))
  }
}
