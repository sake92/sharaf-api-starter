package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
import com.example.petclinic.db.models.*
object PetsDao extends PetsDao
class PetsDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM public.pets".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] = sql"SELECT COUNT(*) FROM public.pets WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[PetsRow]] =
    sql"SELECT id, name, birth_date, type_id, owner_id FROM public.pets".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[PetsRow]] =
    sql"SELECT id, name, birth_date, type_id, owner_id FROM public.pets WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[PetsRow] =
    sql"SELECT id, name, birth_date, type_id, owner_id FROM public.pets WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[PetsRow]] =
    sql"SELECT id, name, birth_date, type_id, owner_id FROM public.pets WHERE $whereQuery".readRowOpt()
  def findById(id: PetsRow.PK): DbAction[PetsRow] =
    sql"SELECT id, name, birth_date, type_id, owner_id FROM public.pets WHERE id = $id".readRow()
  def findByIdOpt(id: PetsRow.PK): DbAction[Option[PetsRow]] =
    sql"SELECT id, name, birth_date, type_id, owner_id FROM public.pets WHERE id = $id".readRowOpt()
  def findByIds(ids: Set[PetsRow.PK]): DbAction[Seq[PetsRow]] = {
    val idsExpr = Query.in(ids)
    sql"SELECT id, name, birth_date, type_id, owner_id FROM public.pets WHERE id IN $idsExpr".readRows()
  }
  def insert(row: PetsRow): DbAction[PetsRow] = sql"""INSERT INTO public.pets(name, birth_date, type_id, owner_id)
    VALUES (
      ${row.name},${row.birth_date},${row.type_id},${row.owner_id}
    )
    RETURNING id, name, birth_date, type_id, owner_id""".insertReturningRow()
  def updateById(row: PetsRow): DbAction[Int] = sql"""UPDATE public.pets
SET name = ${row.name}, birth_date = ${row.birth_date}, type_id = ${row.type_id}, owner_id = ${row.owner_id}
    WHERE id = ${row.id}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM public.pets WHERE $whereQuery".update()
  def deleteById(id: PetsRow.PK): DbAction[Int] = sql"DELETE FROM public.pets WHERE id = $id".update()
  def deleteByIds(ids: Set[PetsRow.PK]): DbAction[Int] = {
    val idsExpr = Query.in(ids)
    sql"DELETE FROM public.pets WHERE id IN $idsExpr".update()
  }
}
