package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
import com.example.petclinic.db.models.*
object VisitsDao extends VisitsDao
class VisitsDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM public.visits".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM public.visits WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[VisitsRow]] =
    sql"SELECT id, pet_id, visit_date, description FROM public.visits".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[VisitsRow]] =
    sql"SELECT id, pet_id, visit_date, description FROM public.visits WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[VisitsRow] =
    sql"SELECT id, pet_id, visit_date, description FROM public.visits WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[VisitsRow]] =
    sql"SELECT id, pet_id, visit_date, description FROM public.visits WHERE $whereQuery".readRowOpt()
  def findById(id: VisitsRow.PK): DbAction[VisitsRow] =
    sql"SELECT id, pet_id, visit_date, description FROM public.visits WHERE id = $id".readRow()
  def findByIdOpt(id: VisitsRow.PK): DbAction[Option[VisitsRow]] =
    sql"SELECT id, pet_id, visit_date, description FROM public.visits WHERE id = $id".readRowOpt()
  def findByIds(ids: Set[VisitsRow.PK]): DbAction[Seq[VisitsRow]] = {
    val idsExpr = Query.in(ids)
    sql"SELECT id, pet_id, visit_date, description FROM public.visits WHERE id IN $idsExpr".readRows()
  }
  def insert(row: VisitsRow): DbAction[VisitsRow] = sql"""INSERT INTO public.visits(pet_id, visit_date, description)
    VALUES (
      ${row.pet_id},${row.visit_date},${row.description}
    )
    RETURNING id, pet_id, visit_date, description""".insertReturningRow()
  def updateById(row: VisitsRow): DbAction[Int] = sql"""UPDATE public.visits
SET pet_id = ${row.pet_id}, visit_date = ${row.visit_date}, description = ${row.description}
    WHERE id = ${row.id}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM public.visits WHERE $whereQuery".update()
  def deleteById(id: VisitsRow.PK): DbAction[Int] = sql"DELETE FROM public.visits WHERE id = $id".update()
  def deleteByIds(ids: Set[VisitsRow.PK]): DbAction[Int] = {
    val idsExpr = Query.in(ids)
    sql"DELETE FROM public.visits WHERE id IN $idsExpr".update()
  }
}
