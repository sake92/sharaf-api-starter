package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
import com.example.petclinic.db.models.*
object UsersDao extends UsersDao
class UsersDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM public.users".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM public.users WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[UsersRow]] = sql"SELECT username, password, enabled FROM public.users".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[UsersRow]] =
    sql"SELECT username, password, enabled FROM public.users WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[UsersRow] =
    sql"SELECT username, password, enabled FROM public.users WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[UsersRow]] =
    sql"SELECT username, password, enabled FROM public.users WHERE $whereQuery".readRowOpt()
  def findById(id: UsersRow.PK): DbAction[UsersRow] =
    sql"SELECT username, password, enabled FROM public.users WHERE username = $id".readRow()
  def findByIdOpt(id: UsersRow.PK): DbAction[Option[UsersRow]] =
    sql"SELECT username, password, enabled FROM public.users WHERE username = $id".readRowOpt()
  def findByIds(ids: Set[UsersRow.PK]): DbAction[Seq[UsersRow]] = {
    val idsExpr = Query.in(ids)
    sql"SELECT username, password, enabled FROM public.users WHERE username IN $idsExpr".readRows()
  }
  def insert(row: UsersRow): DbAction[UsersRow] = sql"""INSERT INTO public.users(username, password, enabled)
    VALUES (
      ${row.username},${row.password},${row.enabled}
    )
    RETURNING username, password, enabled""".insertReturningRow()
  def updateById(row: UsersRow): DbAction[Int] = sql"""UPDATE public.users
SET password = ${row.password}, enabled = ${row.enabled}
    WHERE username = ${row.username}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM public.users WHERE $whereQuery".update()
  def deleteById(id: UsersRow.PK): DbAction[Int] = sql"DELETE FROM public.users WHERE username = $id".update()
  def deleteByIds(ids: Set[UsersRow.PK]): DbAction[Int] = {
    val idsExpr = Query.in(ids)
    sql"DELETE FROM public.users WHERE username IN $idsExpr".update()
  }
}
