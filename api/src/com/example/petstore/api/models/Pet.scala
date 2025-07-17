package com.example.petstore.api.models

import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
import com.example.petstore.db.models.PetsRow

case class Pet(
    id: Option[Long],
    name: String,
    category: Option[Category],
    photoUrls: Seq[String],
    tags: Option[Seq[Tag]],
    status: Option[PetStatus]
) derives JsonRW

object Pet {
  def fromRow(row: PetsRow): Pet = Pet(
    id = Some(row.ID),
    name = row.NAME.getOrElse(""),
    category = None,
    photoUrls = Seq.empty,
    tags = None,
    status = None
  )
}

enum PetStatus derives JsonRW { case available, pending, sold }
