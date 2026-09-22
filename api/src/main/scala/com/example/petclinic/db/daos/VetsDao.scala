package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petclinic.db.models.*
object VetsDao extends VetsDao
class VetsDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.VETS".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.VETS WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[VetsRow]] = sql"SELECT ID, FIRST_NAME, LAST_NAME FROM PUBLIC.VETS".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[VetsRow]] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME FROM PUBLIC.VETS WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[VetsRow] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME FROM PUBLIC.VETS WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[VetsRow]] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME FROM PUBLIC.VETS WHERE $whereQuery".readRowOpt()
  def findById(id: VetsRow.PK): DbAction[VetsRow] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME FROM PUBLIC.VETS WHERE ID = $id".readRow()
  def findByIdOpt(id: VetsRow.PK): DbAction[Option[VetsRow]] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME FROM PUBLIC.VETS WHERE ID = $id".readRowOpt()
  def findByIds(ids: Set[VetsRow.PK]): DbAction[Seq[VetsRow]] = {
    val idsExpr = Query.in(ids)
    sql"SELECT ID, FIRST_NAME, LAST_NAME FROM PUBLIC.VETS WHERE ID IN $idsExpr".readRows()
  }
  def insert(row: VetsRow): DbAction[Int] = sql"""INSERT INTO PUBLIC.VETS(FIRST_NAME, LAST_NAME)
VALUES (
      ${row.FIRST_NAME},${row.LAST_NAME}
)""".insert()
  def updateById(row: VetsRow): DbAction[Int] = sql"""UPDATE PUBLIC.VETS 
SET FIRST_NAME = ${row.FIRST_NAME}, LAST_NAME = ${row.LAST_NAME}
    WHERE ID = ${row.ID}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.VETS WHERE $whereQuery".update()
  def deleteById(id: VetsRow.PK): DbAction[Int] = sql"DELETE FROM PUBLIC.VETS WHERE ID = $id".update()
  def deleteByIds(ids: Set[VetsRow.PK]): DbAction[Int] = {
    val idsExpr = Query.in(ids)
    sql"DELETE FROM PUBLIC.VETS WHERE ID IN $idsExpr".update()
  }
}
