package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
import com.example.petclinic.db.models.*
object RolesDao extends RolesDao
class RolesDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM public.roles".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM public.roles WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[RolesRow]] = sql"SELECT id, username, role FROM public.roles".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[RolesRow]] =
    sql"SELECT id, username, role FROM public.roles WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[RolesRow] =
    sql"SELECT id, username, role FROM public.roles WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[RolesRow]] =
    sql"SELECT id, username, role FROM public.roles WHERE $whereQuery".readRowOpt()
  def findById(id: RolesRow.PK): DbAction[RolesRow] =
    sql"SELECT id, username, role FROM public.roles WHERE id = $id".readRow()
  def findByIdOpt(id: RolesRow.PK): DbAction[Option[RolesRow]] =
    sql"SELECT id, username, role FROM public.roles WHERE id = $id".readRowOpt()
  def findByIds(ids: Set[RolesRow.PK]): DbAction[Seq[RolesRow]] = {
    val idsExpr = Query.in(ids)
    sql"SELECT id, username, role FROM public.roles WHERE id IN $idsExpr".readRows()
  }
  def insert(row: RolesRow): DbAction[RolesRow] = sql"""INSERT INTO public.roles(username, role)
    VALUES (
      ${row.username},${row.role}
    )
    RETURNING id, username, role""".insertReturningRow()
  def updateById(row: RolesRow): DbAction[Int] = sql"""UPDATE public.roles
SET username = ${row.username}, role = ${row.role}
    WHERE id = ${row.id}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM public.roles WHERE $whereQuery".update()
  def deleteById(id: RolesRow.PK): DbAction[Int] = sql"DELETE FROM public.roles WHERE id = $id".update()
  def deleteByIds(ids: Set[RolesRow.PK]): DbAction[Int] = {
    val idsExpr = Query.in(ids)
    sql"DELETE FROM public.roles WHERE id IN $idsExpr".update()
  }
}
