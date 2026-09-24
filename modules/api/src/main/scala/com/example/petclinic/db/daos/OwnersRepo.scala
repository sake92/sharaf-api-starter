package com.example.petclinic.db.daos

import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import com.example.petclinic.db.models.*
import org.postgresql.util.PSQLException

final case class OwnerDetails(owner: OwnersRow, pets: Seq[PetDetails])
final case class PetDetails(pet: PetsRow, petType: TypesRow, visits: Seq[VisitsRow])

enum DeleteOwnerResult {
  case NotFound, HasPets
  case Deleted(owner: OwnerDetails)
}

final class OwnersRepo(dbCtx: SqueryContext) {
  import OwnersRepo.*

  def list(lastName: Option[String]): Seq[OwnerDetails] = dbCtx.run {
    val predicate = lastName match {
      case Some(value) => sql"starts_with(lower(o.last_name), lower($value))"
      case None        => sql"true"
    }
    fetch(predicate)
  }

  def findById(id: Int): Option[OwnerDetails] = dbCtx.run {
    fetch(sql"o.id = $id").headOption
  }

  def insert(row: OwnersRow): OwnerDetails = dbCtx.run {
    OwnerDetails(OwnersDao.insert(row), Seq.empty)
  }

  def update(row: OwnersRow): Option[OwnerDetails] = dbCtx.runTransaction {
    fetch(sql"o.id = ${row.id}", lockOwner = true).headOption.map { current =>
      OwnersDao.updateById(row)
      current.copy(owner = row)
    }
  }

  def delete(id: Int): DeleteOwnerResult =
    try
      dbCtx.run {
        sql"""DELETE FROM public.owners WHERE id = $id
        RETURNING id, first_name, last_name, address, city, telephone"""
          .deleteReturningRows[OwnersRow]()
          .headOption match {
          case Some(row) => DeleteOwnerResult.Deleted(OwnerDetails(row, Seq.empty))
          case None      => DeleteOwnerResult.NotFound
        }
      }
    catch {
      case error: PSQLException
          if error.getSQLState == "23503" && Option(error.getServerErrorMessage).exists(
            _.getConstraint == "pets_owner_id_fkey"
          ) =>
        DeleteOwnerResult.HasPets
    }

  private def fetch(predicate: Query, lockOwner: Boolean = false)(using SqueryConnection): Seq[OwnerDetails] = {
    val lock = Query.when(lockOwner)(sql"FOR UPDATE OF o")
    val rows = sql"""SELECT ${OwnersRow.allColsWithPrefix("o")},
                           ${PetsRow.allColsWithPrefix("p")},
                           ${TypesRow.allColsWithPrefix("t")},
                           ${VisitsRow.allColsWithPrefix("v")}
      FROM public.owners o
      LEFT JOIN public.pets p ON p.owner_id = o.id
      LEFT JOIN public.types t ON t.id = p.type_id
      LEFT JOIN public.visits v ON v.pet_id = p.id
      WHERE $predicate
      ORDER BY o.id, p.id, v.id
      $lock""".readRows[JoinedOwnerRow]()

    rows.groupBy(_.o.id).toSeq.sortBy(_._1).map { (_, ownerRows) =>
      val pets = ownerRows
        .flatMap(row => for pet <- row.p; petType <- row.t yield (pet, petType, row.v))
        .groupBy(_._1.id)
        .toSeq
        .sortBy(_._1)
        .map { (_, petRows) =>
          val (pet, petType, _) = petRows.head
          PetDetails(pet, petType, petRows.flatMap(_._3))
        }
      OwnerDetails(ownerRows.head.o, pets)
    }
  }
}

object OwnersRepo {
  private final case class JoinedOwnerRow(
      o: OwnersRow,
      p: Option[PetsRow],
      t: Option[TypesRow],
      v: Option[VisitsRow]
  ) derives SqlReadRow
}
