package com.example.petclinic.client.clients
import sttp.client4.*
import sttp.client4.circe.*
import com.example.petclinic.client.models.*
object PetClient { val server1: String = "/" }
class PetClient(baseUrl: String) {
  def addPetToOwner(ownerId: Int, petFields: PetFields): Request[Either[ResponseException[String], Pet]] = {
    basicRequest.post(uri"$baseUrl/owners/$ownerId/pets").body(asJson(petFields)).response(asJson[Pet])
  }
  def getOwnersPet(ownerId: Int, petId: Int): Request[Either[ResponseException[String], Pet]] = {
    basicRequest.get(uri"$baseUrl/owners/$ownerId/pets/$petId").response(asJson[Pet])
  }
  def updateOwnersPet(
      ownerId: Int,
      petId: Int,
      petFields: PetFields
  ): Request[Either[ResponseException[String], Unit]] = {
    basicRequest
      .put(uri"$baseUrl/owners/$ownerId/pets/$petId")
      .body(asJson(petFields))
      .response(asString.mapWithMetadata(ResponseAs.deserializeRightCatchingExceptions(_ => ())))
  }
  def listPets(): Request[Either[ResponseException[String], Seq[Pet]]] = {
    basicRequest.get(uri"$baseUrl/pets").response(asJson[Seq[Pet]])
  }
  def getPet(petId: Int): Request[Either[ResponseException[String], Pet]] = {
    basicRequest.get(uri"$baseUrl/pets/$petId").response(asJson[Pet])
  }
  def updatePet(petId: Int, pet: Pet): Request[Either[ResponseException[String], Pet]] = {
    basicRequest.put(uri"$baseUrl/pets/$petId").body(asJson(pet)).response(asJson[Pet])
  }
  def deletePet(petId: Int): Request[Either[ResponseException[String], Pet]] = {
    basicRequest.delete(uri"$baseUrl/pets/$petId").response(asJson[Pet])
  }
}
