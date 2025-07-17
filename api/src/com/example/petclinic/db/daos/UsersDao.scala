package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petclinic.db.models.*
object UsersDao extends UsersDao
class UsersDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.USERS".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM PUBLIC.USERS WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[UsersRow]] = sql"SELECT USERNAME, PASSWORD, ENABLED FROM PUBLIC.USERS".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[UsersRow]] =
    sql"SELECT USERNAME, PASSWORD, ENABLED FROM PUBLIC.USERS WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[UsersRow] =
    sql"SELECT USERNAME, PASSWORD, ENABLED FROM PUBLIC.USERS WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[UsersRow]] =
    sql"SELECT USERNAME, PASSWORD, ENABLED FROM PUBLIC.USERS WHERE $whereQuery".readRowOpt()
  def findById(id: UsersRow.PK): DbAction[UsersRow] =
    sql"SELECT USERNAME, PASSWORD, ENABLED FROM PUBLIC.USERS WHERE USERNAME = $id".readRow()
  def findByIdOpt(id: UsersRow.PK): DbAction[Option[UsersRow]] =
    sql"SELECT USERNAME, PASSWORD, ENABLED FROM PUBLIC.USERS WHERE USERNAME = $id".readRowOpt()
  def findByIds(ids: Set[UsersRow.PK]): DbAction[Seq[UsersRow]] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"SELECT USERNAME, PASSWORD, ENABLED FROM PUBLIC.USERS WHERE USERNAME IN ($idsExpr)".readRows()
  }
  def insert(row: UsersRow): DbAction[Int] = sql"""INSERT INTO PUBLIC.USERS(USERNAME, PASSWORD, ENABLED) 
VALUES (
      ${row.USERNAME},${row.PASSWORD},${row.ENABLED}    
)""".insert()
  def updateById(row: UsersRow): DbAction[Int] = sql"""UPDATE PUBLIC.USERS 
SET PASSWORD = ${row.PASSWORD}, ENABLED = ${row.ENABLED}
    WHERE USERNAME = ${row.USERNAME}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.USERS WHERE $whereQuery".update()
  def deleteById(id: UsersRow.PK): DbAction[Int] = sql"DELETE FROM PUBLIC.USERS WHERE USERNAME = $id".update()
  def deleteByIds(ids: Set[UsersRow.PK]): DbAction[Int] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"DELETE FROM PUBLIC.USERS WHERE USERNAME IN ($idsExpr)".update()
  }
}
