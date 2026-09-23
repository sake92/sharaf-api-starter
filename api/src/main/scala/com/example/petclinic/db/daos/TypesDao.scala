package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
import com.example.petclinic.db.models.*
object TypesDao extends TypesDao
class TypesDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM public.types".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM public.types WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[TypesRow]] = sql"SELECT id, name FROM public.types".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[TypesRow]] =
    sql"SELECT id, name FROM public.types WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[TypesRow] =
    sql"SELECT id, name FROM public.types WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[TypesRow]] =
    sql"SELECT id, name FROM public.types WHERE $whereQuery".readRowOpt()
  def findById(id: TypesRow.PK): DbAction[TypesRow] = sql"SELECT id, name FROM public.types WHERE id = $id".readRow()
  def findByIdOpt(id: TypesRow.PK): DbAction[Option[TypesRow]] =
    sql"SELECT id, name FROM public.types WHERE id = $id".readRowOpt()
  def findByIds(ids: Set[TypesRow.PK]): DbAction[Seq[TypesRow]] = {
    val idsExpr = Query.in(ids)
    sql"SELECT id, name FROM public.types WHERE id IN $idsExpr".readRows()
  }
  def insert(row: TypesRow): DbAction[TypesRow] = sql"""INSERT INTO public.types(name)
    VALUES (
      ${row.name}
    )
    RETURNING id, name""".insertReturningRow()
  def updateById(row: TypesRow): DbAction[Int] = sql"""UPDATE public.types
SET name = ${row.name}
    WHERE id = ${row.id}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM public.types WHERE $whereQuery".update()
  def deleteById(id: TypesRow.PK): DbAction[Int] = sql"DELETE FROM public.types WHERE id = $id".update()
  def deleteByIds(ids: Set[TypesRow.PK]): DbAction[Int] = {
    val idsExpr = Query.in(ids)
    sql"DELETE FROM public.types WHERE id IN $idsExpr".update()
  }
}
