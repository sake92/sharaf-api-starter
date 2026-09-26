package com.example.petclinic.client.clients
import sttp.client4.*
import ba.sake.tupson.{given, *}
import ba.sake.sttp.tupson.*
import com.example.petclinic.client.models.*
object OwnerClient { val server1: String = "/" }
class OwnerClient(baseUrl: String) {
  def listOwners(lastName: Option[String]): Request[Either[ResponseException[String], Seq[Owner]]] = {
    basicRequest.get(uri"$baseUrl/owners?lastName=$lastName").response(asJson[Seq[Owner]])
  }
  def addOwner(ownerFields: OwnerFields): Request[Either[ResponseException[String], Owner]] = {
    basicRequest.post(uri"$baseUrl/owners").body(asJson(ownerFields)).response(asJson[Owner])
  }
  def getOwner(ownerId: Int): Request[Either[ResponseException[String], Owner]] = {
    basicRequest.get(uri"$baseUrl/owners/$ownerId").response(asJson[Owner])
  }
  def updateOwner(ownerId: Int, ownerFields: OwnerFields): Request[Either[ResponseException[String], Owner]] = {
    basicRequest.put(uri"$baseUrl/owners/$ownerId").body(asJson(ownerFields)).response(asJson[Owner])
  }
  def deleteOwner(ownerId: Int): Request[Either[ResponseException[String], Owner]] = {
    basicRequest.delete(uri"$baseUrl/owners/$ownerId").response(asJson[Owner])
  }
}
