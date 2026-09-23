package com.example.petclinic.client.clients
import sttp.client4.*
import sttp.client4.circe.*
import com.example.petclinic.client.models.*
object PettypesClient { val server1: String = "/" }
class PettypesClient(baseUrl: String) {
  def listPetTypes(): Request[Either[ResponseException[String], Seq[PetType]]] = {
    basicRequest.get(uri"$baseUrl/pettypes").response(asJson[Seq[PetType]])
  }
  def addPetType(petTypeFields: PetTypeFields): Request[Either[ResponseException[String], PetType]] = {
    basicRequest.post(uri"$baseUrl/pettypes").body(asJson(petTypeFields)).response(asJson[PetType])
  }
  def getPetType(petTypeId: Int): Request[Either[ResponseException[String], PetType]] = {
    basicRequest.get(uri"$baseUrl/pettypes/$petTypeId").response(asJson[PetType])
  }
  def updatePetType(petTypeId: Int, petType: PetType): Request[Either[ResponseException[String], PetType]] = {
    basicRequest.put(uri"$baseUrl/pettypes/$petTypeId").body(asJson(petType)).response(asJson[PetType])
  }
  def deletePetType(petTypeId: Int): Request[Either[ResponseException[String], PetType]] = {
    basicRequest.delete(uri"$baseUrl/pettypes/$petTypeId").response(asJson[PetType])
  }
}
