package com.example.petclinic.api.controllers

import ba.sake.querson.QueryStringRW
import ba.sake.sharaf.*, routing.*
import ba.sake.sharaf.exceptions.NotFoundException
import ba.sake.squery.{*, given}
import com.example.petclinic.api.models.*
import com.example.petclinic.db.daos.OwnersDao
import com.example.petclinic.db.models.*
import sttp.model.StatusCode
import ba.sake.validson.Validator
import java.time._
import java.util.UUID

class OwnerController(dbCtx: SqueryContext) {
  def routes = Routes {
    case GET -> Path("owners") =>
      case class QP(lastName: Option[String]) derives QueryStringRW
      val qp = Request.current.queryParamsValidated[QP]
      val owners = dbCtx.run {
        val rows = (qp.lastName match {
          case Some(lastName) =>
            OwnersDao.findAllWhere(sql"starts_with(lower(last_name), lower($lastName))")
          case None => OwnersDao.findAll()
        }).sortBy(_.id)
        withPets(rows)
      }
      Response.withBody(owners)

    case POST -> Path("owners") =>
      val reqBody = Request.current.bodyJsonValidated[OwnerFields]
      val owner = dbCtx.run {
        val row = OwnersDao.insert(toRow(0, reqBody))
        toOwner(row, Seq.empty)
      }
      Response.withStatus(StatusCode.Created).withBody(owner)

    case GET -> Path("owners", param[Int](ownerId)) =>
      if ownerId < 0 then invalidId
      else {
        val owner = dbCtx.run {
          val row = OwnersDao.findByIdOpt(ownerId).getOrElse(throw NotFoundException(s"Owner with ID $ownerId"))
          withPets(Seq(row)).head
        }
        Response.withBody(owner)
      }

    case PUT -> Path("owners", param[Int](ownerId)) =>
      if ownerId < 0 then invalidId
      else {
        val reqBody = Request.current.bodyJsonValidated[OwnerFields]
        val owner = dbCtx.runTransaction {
          lockedOwner(ownerId).getOrElse(throw NotFoundException(s"Owner with ID $ownerId"))
          val row = toRow(ownerId, reqBody)
          OwnersDao.updateById(row)
          withPets(Seq(row)).head
        }
        Response.withBody(owner)
      }

    case DELETE -> Path("owners", param[Int](ownerId)) =>
      if ownerId < 0 then invalidId
      else {
        val ownerOpt = dbCtx.runTransaction {
          val row = lockedOwner(ownerId).getOrElse(throw NotFoundException(s"Owner with ID $ownerId"))
          val owner = withPets(Seq(row)).head
          if owner.pets.nonEmpty then None
          else {
            OwnersDao.deleteById(ownerId)
            Some(owner)
          }
        }
        ownerOpt match {
          case Some(owner) => Response.withBody(owner)
          case None =>
            ApiProblem.response(StatusCode.Conflict, s"Owner with ID $ownerId still has pets")
        }
      }
  }

  private def toRow(id: Int, fields: OwnerFields): OwnersRow =
    OwnersRow(id, fields.firstName, fields.lastName, fields.address, fields.city, fields.telephone)

  private def invalidId = ApiProblem.response(StatusCode.BadRequest, "Owner ID must be non-negative")

  private def toOwner(row: OwnersRow, pets: Seq[Pet]): Owner =
    Owner(row.first_name, row.last_name, row.address, row.city, row.telephone, Some(row.id), pets)

  private def lockedOwner(id: Int)(using SqueryConnection): Option[OwnersRow] =
    sql"""SELECT id, first_name, last_name, address, city, telephone
      FROM public.owners WHERE id = $id FOR UPDATE""".readRowOpt[OwnersRow]()

  private def withPets(rows: Seq[OwnersRow])(using SqueryConnection): Seq[Owner] = {
    val petsByOwner =
      if rows.isEmpty then Map.empty[Int, Seq[PetAndVisitsRow]]
      else {
        val ownerIds = Query.in(rows.map(_.id).toSet)
        sql"""SELECT ${PetsRow.allColsWithPrefix("p")},
                     ${TypesRow.allColsWithPrefix("t")},
                     ${VisitsRow.allColsWithPrefix("v")}
          FROM public.pets p
          JOIN public.types t ON t.id = p.type_id
          LEFT JOIN public.visits v ON v.pet_id = p.id
          WHERE p.owner_id IN $ownerIds
          ORDER BY p.owner_id, p.id, v.id"""
          .readRows[PetAndVisitsRow]()
          .groupBy(_.p.owner_id)
      }
    rows.map { row =>
      val pets = petsByOwner
        .getOrElse(row.id, Seq.empty)
        .groupBy(_.p.id)
        .values
        .map { petRows =>
          val first = petRows.head
          Pet.fromRow(first.p, first.t, petRows.flatMap(_.v))
        }
        .toSeq
        .sortBy(_.id)
      toOwner(row, pets)
    }
  }
}
