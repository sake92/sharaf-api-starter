package com.example.petclinic.client.clients
import sttp.client4.*
import sttp.client4.circe.*
import com.example.petclinic.client.models.*
object VetClient { val server1: String = "/" }
class VetClient(baseUrl: String) {
  def listVets(): Request[Either[ResponseException[String], Seq[Vet]]] = {
    basicRequest.get(uri"$baseUrl/vets").response(asJson[Seq[Vet]])
  }
  def addVet(vet: Vet): Request[Either[ResponseException[String], Vet]] = {
    basicRequest.post(uri"$baseUrl/vets").body(asJson(vet)).response(asJson[Vet])
  }
  def getVet(vetId: Int): Request[Either[ResponseException[String], Vet]] = {
    basicRequest.get(uri"$baseUrl/vets/$vetId").response(asJson[Vet])
  }
  def updateVet(vetId: Int, vet: Vet): Request[Either[ResponseException[String], Vet]] = {
    basicRequest.put(uri"$baseUrl/vets/$vetId").body(asJson(vet)).response(asJson[Vet])
  }
  def deleteVet(vetId: Int): Request[Either[ResponseException[String], Vet]] = {
    basicRequest.delete(uri"$baseUrl/vets/$vetId").response(asJson[Vet])
  }
}
