package com.example.petclinic.client.clients
import sttp.client4.*
import sttp.client4.circe.*
import com.example.petclinic.client.models.*
object SpecialtyClient { val server1: String = "/" }
class SpecialtyClient(baseUrl: String) {
  def listSpecialties(): Request[Either[ResponseException[String], Seq[Specialty]]] = {
    basicRequest.get(uri"$baseUrl/specialties").response(asJson[Seq[Specialty]])
  }
  def addSpecialty(specialty: Specialty): Request[Either[ResponseException[String], Specialty]] = {
    basicRequest.post(uri"$baseUrl/specialties").body(asJson(specialty)).response(asJson[Specialty])
  }
  def getSpecialty(specialtyId: Int): Request[Either[ResponseException[String], Specialty]] = {
    basicRequest.get(uri"$baseUrl/specialties/$specialtyId").response(asJson[Specialty])
  }
  def updateSpecialty(specialtyId: Int, specialty: Specialty): Request[Either[ResponseException[String], Specialty]] = {
    basicRequest.put(uri"$baseUrl/specialties/$specialtyId").body(asJson(specialty)).response(asJson[Specialty])
  }
  def deleteSpecialty(specialtyId: Int): Request[Either[ResponseException[String], Specialty]] = {
    basicRequest.delete(uri"$baseUrl/specialties/$specialtyId").response(asJson[Specialty])
  }
}
