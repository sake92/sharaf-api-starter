package com.example.petclinic.client.clients
import sttp.client4.*
import sttp.client4.circe.*
import com.example.petclinic.client.models.*
object VisitClient { val server1: String = "/" }
class VisitClient(baseUrl: String) {
  def addVisitToOwner(
      ownerId: Int,
      petId: Int,
      visitFields: VisitFields
  ): Request[Either[ResponseException[String], Visit]] = {
    basicRequest
      .post(uri"$baseUrl/owners/$ownerId/pets/$petId/visits")
      .body(asJson(visitFields))
      .response(asJson[Visit])
  }
  def listVisits(): Request[Either[ResponseException[String], Seq[Visit]]] = {
    basicRequest.get(uri"$baseUrl/visits").response(asJson[Seq[Visit]])
  }
  def addVisit(visit: Visit): Request[Either[ResponseException[String], Visit]] = {
    basicRequest.post(uri"$baseUrl/visits").body(asJson(visit)).response(asJson[Visit])
  }
  def getVisit(visitId: Int): Request[Either[ResponseException[String], Visit]] = {
    basicRequest.get(uri"$baseUrl/visits/$visitId").response(asJson[Visit])
  }
  def updateVisit(visitId: Int, visit: Visit): Request[Either[ResponseException[String], Visit]] = {
    basicRequest.put(uri"$baseUrl/visits/$visitId").body(asJson(visit)).response(asJson[Visit])
  }
  def deleteVisit(visitId: Int): Request[Either[ResponseException[String], Visit]] = {
    basicRequest.delete(uri"$baseUrl/visits/$visitId").response(asJson[Visit])
  }
}
