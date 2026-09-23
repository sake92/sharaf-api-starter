package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
import com.example.petclinic.db.models.*
object VetsDao extends VetsDao
class VetsDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM public.vets".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] = sql"SELECT COUNT(*) FROM public.vets WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[VetsRow]] = sql"SELECT id, first_name, last_name FROM public.vets".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[VetsRow]] =
    sql"SELECT id, first_name, last_name FROM public.vets WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[VetsRow] =
    sql"SELECT id, first_name, last_name FROM public.vets WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[VetsRow]] =
    sql"SELECT id, first_name, last_name FROM public.vets WHERE $whereQuery".readRowOpt()
  def findById(id: VetsRow.PK): DbAction[VetsRow] =
    sql"SELECT id, first_name, last_name FROM public.vets WHERE id = $id".readRow()
  def findByIdOpt(id: VetsRow.PK): DbAction[Option[VetsRow]] =
    sql"SELECT id, first_name, last_name FROM public.vets WHERE id = $id".readRowOpt()
  def findByIds(ids: Set[VetsRow.PK]): DbAction[Seq[VetsRow]] = {
    val idsExpr = Query.in(ids)
    sql"SELECT id, first_name, last_name FROM public.vets WHERE id IN $idsExpr".readRows()
  }
  def insert(row: VetsRow): DbAction[VetsRow] = sql"""INSERT INTO public.vets(first_name, last_name)
    VALUES (
      ${row.first_name},${row.last_name}
    )
    RETURNING id, first_name, last_name""".insertReturningRow()
  def updateById(row: VetsRow): DbAction[Int] = sql"""UPDATE public.vets
SET first_name = ${row.first_name}, last_name = ${row.last_name}
    WHERE id = ${row.id}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM public.vets WHERE $whereQuery".update()
  def deleteById(id: VetsRow.PK): DbAction[Int] = sql"DELETE FROM public.vets WHERE id = $id".update()
  def deleteByIds(ids: Set[VetsRow.PK]): DbAction[Int] = {
    val idsExpr = Query.in(ids)
    sql"DELETE FROM public.vets WHERE id IN $idsExpr".update()
  }
}
