package com.example.petstore.api.controllers

import java.time.*
import java.util.UUID
import sttp.model.StatusCode
import ba.sake.querson.QueryStringRW
import ba.sake.validson.Validator
import ba.sake.sharaf.*, routing.*
import ba.sake.squery.{*, given}
import ba.sake.squery.utils.*
import com.example.petstore.api.models.*
import com.example.petstore.db.models.*
import com.example.petstore.db.daos.*

class PetController(dbCtx: SqueryContext) {
  def routes = Routes {
    case POST -> Path("owners", param[Int](ownerId), "pets") =>
      val reqBody = Request.current.bodyJsonValidated[PetFields]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Pet")
    case GET -> Path("owners", param[Int](ownerId), "pets", param[Int](petId)) =>
      dbCtx.run {
        val rows = sql"""
          SELECT ${PetsRow.allColsWithPrefix("p")},
                 ${TypesRow.allColsWithPrefix("t")},
                 ${VisitsRow.allColsWithPrefix("v")}
          FROM ${PetsRow.tableName} p
          JOIN ${TypesRow.tableName} t ON p.TYPE_ID = t.ID
          LEFT JOIN ${VisitsRow.tableName} v ON v.PET_ID = p.ID
          WHERE p.ID = $petId AND p.OWNER_ID = $ownerId
        """.readRows[PetAndVisitsRow]()

        val res = rows.groupByOrderedOpt(r => (r.p, r.t), _.v)
        res.headOption match {
          case Some(((p, t), visitsRows)) =>
            val pet = Pet.fromRow(p, t, visitsRows.toSeq)
            Response.withStatus(StatusCode.Ok).withBody(pet)
          case None =>
            Response.withStatus(StatusCode.NotFound).withBody(s"Pet with id $petId not found for owner $ownerId")
        }
      }
    case PUT -> Path("owners", param[Int](ownerId), "pets", param[Int](petId)) =>
      val reqBody = Request.current.bodyJsonValidated[PetFields]
      Response.withStatus(StatusCode.NotImplemented)
    case GET -> Path("pets") =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Seq[Pet]")
    case GET -> Path("pets", param[Int](petId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Pet")
    case PUT -> Path("pets", param[Int](petId)) =>
      val reqBody = Request.current.bodyJsonValidated[Pet]
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Pet")
    case DELETE -> Path("pets", param[Int](petId)) =>
      Response.withStatus(StatusCode.NotImplemented).withBody("TODO: return Pet")
  }
}
