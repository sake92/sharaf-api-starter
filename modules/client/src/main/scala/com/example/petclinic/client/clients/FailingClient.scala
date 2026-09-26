package com.example.petclinic.client.clients
import sttp.client4.*
import ba.sake.tupson.{given, *}
import ba.sake.sttp.tupson.*
import com.example.petclinic.client.models.*
object FailingClient { val server1: String = "/" }
class FailingClient(baseUrl: String) {
  def failingRequest(): Request[Either[ResponseException[String], Unit]] = {
    basicRequest
      .get(uri"$baseUrl/oops")
      .response(asString.mapWithMetadata(ResponseAs.deserializeRightCatchingExceptions(_ => ())))
  }
}
