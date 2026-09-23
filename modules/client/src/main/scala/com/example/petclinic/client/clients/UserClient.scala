package com.example.petclinic.client.clients
import sttp.client4.*
import sttp.client4.circe.*
import com.example.petclinic.client.models.*
object UserClient { val server1: String = "/" }
class UserClient(baseUrl: String) {
  def addUser(user: User): Request[Either[ResponseException[String], User]] = {
    basicRequest.post(uri"$baseUrl/users").body(asJson(user)).response(asJson[User])
  }
}
