package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petclinic.db.models.*
object RolesDao extends RolesDao
class RolesDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.ROLES".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM PUBLIC.ROLES WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[RolesRow]] = sql"SELECT ID, USERNAME, ROLE FROM PUBLIC.ROLES".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[RolesRow]] =
    sql"SELECT ID, USERNAME, ROLE FROM PUBLIC.ROLES WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[RolesRow] =
    sql"SELECT ID, USERNAME, ROLE FROM PUBLIC.ROLES WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[RolesRow]] =
    sql"SELECT ID, USERNAME, ROLE FROM PUBLIC.ROLES WHERE $whereQuery".readRowOpt()
  def findById(id: RolesRow.PK): DbAction[RolesRow] =
    sql"SELECT ID, USERNAME, ROLE FROM PUBLIC.ROLES WHERE ID = $id".readRow()
  def findByIdOpt(id: RolesRow.PK): DbAction[Option[RolesRow]] =
    sql"SELECT ID, USERNAME, ROLE FROM PUBLIC.ROLES WHERE ID = $id".readRowOpt()
  def findByIds(ids: Set[RolesRow.PK]): DbAction[Seq[RolesRow]] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"SELECT ID, USERNAME, ROLE FROM PUBLIC.ROLES WHERE ID IN ($idsExpr)".readRows()
  }
  def insert(row: RolesRow): DbAction[Int] = sql"""INSERT INTO PUBLIC.ROLES(ID, USERNAME, ROLE) 
VALUES (
      ${row.ID},${row.USERNAME},${row.ROLE}    
)""".insert()
  def updateById(row: RolesRow): DbAction[Int] = sql"""UPDATE PUBLIC.ROLES 
SET USERNAME = ${row.USERNAME}, ROLE = ${row.ROLE}
    WHERE ID = ${row.ID}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.ROLES WHERE $whereQuery".update()
  def deleteById(id: RolesRow.PK): DbAction[Int] = sql"DELETE FROM PUBLIC.ROLES WHERE ID = $id".update()
  def deleteByIds(ids: Set[RolesRow.PK]): DbAction[Int] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"DELETE FROM PUBLIC.ROLES WHERE ID IN ($idsExpr)".update()
  }
}
