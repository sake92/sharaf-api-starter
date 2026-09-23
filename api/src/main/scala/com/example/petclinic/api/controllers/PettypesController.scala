package com.example.petclinic.api.controllers

import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import ba.sake.squery.{*, given}
import ba.sake.squery.utils.*
import com.example.petclinic.api.models.*
import com.example.petclinic.db.models.*
import com.example.petclinic.db.daos.*

class PettypesController(dbCtx: SqueryContext) {
  def routes = Routes {
    case GET -> Path("pettypes") =>
      val petTypes = dbCtx.run {
        TypesDao.findAll().map(PetType.fromRow)
      }
      Response.withBody(petTypes)
    case GET -> Path("pettypes", param[Int](petTypeId)) =>
      val petTypeRowOpt = dbCtx.run {
        TypesDao.findByIdOpt(petTypeId)
      }
      petTypeRowOpt match
        case Some(petTypeRow) => Response.withBody(PetType.fromRow(petTypeRow))
        case None => Response.withStatus(StatusCode.NotFound).withBody(s"PetType with ID $petTypeId not found")
    case POST -> Path("pettypes") =>
      val reqBody = Request.current.bodyJsonValidated[PetTypeFields]
      val newRow = dbCtx.run {
        TypesDao.insert(TypesRow(0, reqBody.name))
      }
      val res = PetType.fromRow(newRow)
      Response.withBody(res)
    case PUT -> Path("pettypes", param[Int](petTypeId)) =>
      val reqBody = Request.current.bodyJsonValidated[PetType]
      val row = dbCtx.runTransaction {
        val row = TypesDao.findById(petTypeId)
        val updatedRow = row.copy(name = reqBody.name)
        TypesDao.updateById(updatedRow)
        updatedRow
      }
      Response.withBody(PetType.fromRow(row))
    case DELETE -> Path("pettypes", param[Int](petTypeId)) =>
      val row = dbCtx.runTransaction {
        val row = TypesDao.findById(petTypeId)
        TypesDao.deleteById(petTypeId)
        row
      }
      Response.withBody(PetType.fromRow(row))
  }
}
