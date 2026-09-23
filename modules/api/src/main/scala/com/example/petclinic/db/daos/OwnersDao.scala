package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
import com.example.petclinic.db.models.*
object OwnersDao extends OwnersDao
class OwnersDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM public.owners".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM public.owners WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[OwnersRow]] =
    sql"SELECT id, first_name, last_name, address, city, telephone FROM public.owners".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[OwnersRow]] =
    sql"SELECT id, first_name, last_name, address, city, telephone FROM public.owners WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[OwnersRow] =
    sql"SELECT id, first_name, last_name, address, city, telephone FROM public.owners WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[OwnersRow]] =
    sql"SELECT id, first_name, last_name, address, city, telephone FROM public.owners WHERE $whereQuery".readRowOpt()
  def findById(id: OwnersRow.PK): DbAction[OwnersRow] =
    sql"SELECT id, first_name, last_name, address, city, telephone FROM public.owners WHERE id = $id".readRow()
  def findByIdOpt(id: OwnersRow.PK): DbAction[Option[OwnersRow]] =
    sql"SELECT id, first_name, last_name, address, city, telephone FROM public.owners WHERE id = $id".readRowOpt()
  def findByIds(ids: Set[OwnersRow.PK]): DbAction[Seq[OwnersRow]] = {
    val idsExpr = Query.in(ids)
    sql"SELECT id, first_name, last_name, address, city, telephone FROM public.owners WHERE id IN $idsExpr".readRows()
  }
  def insert(row: OwnersRow): DbAction[OwnersRow] =
    sql"""INSERT INTO public.owners(first_name, last_name, address, city, telephone)
    VALUES (
      ${row.first_name},${row.last_name},${row.address},${row.city},${row.telephone}
    )
    RETURNING id, first_name, last_name, address, city, telephone""".insertReturningRow()
  def updateById(row: OwnersRow): DbAction[Int] = sql"""UPDATE public.owners
SET first_name = ${row.first_name}, last_name = ${row.last_name}, address = ${row.address}, city = ${row.city}, telephone = ${row.telephone}
    WHERE id = ${row.id}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM public.owners WHERE $whereQuery".update()
  def deleteById(id: OwnersRow.PK): DbAction[Int] = sql"DELETE FROM public.owners WHERE id = $id".update()
  def deleteByIds(ids: Set[OwnersRow.PK]): DbAction[Int] = {
    val idsExpr = Query.in(ids)
    sql"DELETE FROM public.owners WHERE id IN $idsExpr".update()
  }
}
