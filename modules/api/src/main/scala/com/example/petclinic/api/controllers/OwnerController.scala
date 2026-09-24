package com.example.petclinic.api.controllers

import ba.sake.querson.QueryStringRW
import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.exceptions.NotFoundException
import com.example.petclinic.api.models.*
import com.example.petclinic.db.daos.{DeleteOwnerResult, OwnerDetails, OwnersRepo}
import com.example.petclinic.db.models.OwnersRow
import sttp.model.StatusCode
import ba.sake.validson.Validator
import java.time._
import java.util.UUID

class OwnerController(repo: OwnersRepo) {
  def routes = Routes {
    case GET -> Path("owners") =>
      case class QP(lastName: Option[String]) derives QueryStringRW
      val qp = Request.current.queryParamsValidated[QP]
      Response.withBody(repo.list(qp.lastName).map(toOwner))

    case POST -> Path("owners") =>
      val reqBody = Request.current.bodyJsonValidated[OwnerFields]
      val owner = repo.insert(toRow(0, reqBody))
      Response.withStatus(StatusCode.Created).withBody(toOwner(owner))

    case GET -> Path("owners", param[Int](ownerId)) =>
      if ownerId < 0 then invalidId
      else {
        val owner = repo.findById(ownerId).getOrElse(throw NotFoundException(s"Owner with ID $ownerId"))
        Response.withBody(toOwner(owner))
      }

    case PUT -> Path("owners", param[Int](ownerId)) =>
      if ownerId < 0 then invalidId
      else {
        val reqBody = Request.current.bodyJsonValidated[OwnerFields]
        val owner = repo.update(toRow(ownerId, reqBody)).getOrElse(throw NotFoundException(s"Owner with ID $ownerId"))
        Response.withBody(toOwner(owner))
      }

    case DELETE -> Path("owners", param[Int](ownerId)) =>
      if ownerId < 0 then invalidId
      else {
        repo.delete(ownerId) match {
          case DeleteOwnerResult.Deleted(owner) => Response.withBody(toOwner(owner))
          case DeleteOwnerResult.NotFound       => throw NotFoundException(s"Owner with ID $ownerId")
          case DeleteOwnerResult.HasPets =>
            ApiProblem.response(StatusCode.Conflict, s"Owner with ID $ownerId still has pets")
        }
      }
  }

  private def invalidId = ApiProblem.response(StatusCode.BadRequest, "Owner ID must be non-negative")

  private def toRow(id: Int, fields: OwnerFields): OwnersRow =
    OwnersRow(id, fields.firstName, fields.lastName, fields.address, fields.city, fields.telephone)

  private def toOwner(details: OwnerDetails): Owner = {
    val row = details.owner
    val pets = details.pets.map(p => Pet.fromRow(p.pet, p.petType, p.visits))
    Owner(row.first_name, row.last_name, row.address, row.city, row.telephone, Some(row.id), pets)
  }
}
