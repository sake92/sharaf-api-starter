package com.example.petstore.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petstore.db.models.*
object OwnersDao extends OwnersDao
class OwnersDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.OWNERS".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM PUBLIC.OWNERS WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[OwnersRow]] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE FROM PUBLIC.OWNERS".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[OwnersRow]] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE FROM PUBLIC.OWNERS WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[OwnersRow] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE FROM PUBLIC.OWNERS WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[OwnersRow]] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE FROM PUBLIC.OWNERS WHERE $whereQuery".readRowOpt()
  def findById(id: OwnersRow.PK): DbAction[OwnersRow] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE FROM PUBLIC.OWNERS WHERE ID = $id".readRow()
  def findByIdOpt(id: OwnersRow.PK): DbAction[Option[OwnersRow]] =
    sql"SELECT ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE FROM PUBLIC.OWNERS WHERE ID = $id".readRowOpt()
  def findByIds(ids: Set[OwnersRow.PK]): DbAction[Seq[OwnersRow]] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"SELECT ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE FROM PUBLIC.OWNERS WHERE ID IN ($idsExpr)".readRows()
  }
  def insert(row: OwnersRow): DbAction[Int] =
    sql"""INSERT INTO PUBLIC.OWNERS(ID, FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE) 
VALUES (
      ${row.ID},${row.FIRST_NAME},${row.LAST_NAME},${row.ADDRESS},${row.CITY},${row.TELEPHONE}    
)""".insert()
  def updateById(row: OwnersRow): DbAction[Int] = sql"""UPDATE PUBLIC.OWNERS 
SET FIRST_NAME = ${row.FIRST_NAME}, LAST_NAME = ${row.LAST_NAME}, ADDRESS = ${row.ADDRESS}, CITY = ${row.CITY}, TELEPHONE = ${row.TELEPHONE}
    WHERE ID = ${row.ID}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.OWNERS WHERE $whereQuery".update()
  def deleteById(id: OwnersRow.PK): DbAction[Int] = sql"DELETE FROM PUBLIC.OWNERS WHERE ID = $id".update()
  def deleteByIds(ids: Set[OwnersRow.PK]): DbAction[Int] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"DELETE FROM PUBLIC.OWNERS WHERE ID IN ($idsExpr)".update()
  }
}
