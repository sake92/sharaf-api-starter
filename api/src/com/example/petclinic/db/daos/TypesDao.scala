package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petclinic.db.models.*
object TypesDao extends TypesDao
class TypesDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.TYPES".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM PUBLIC.TYPES WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[TypesRow]] = sql"SELECT ID, NAME FROM PUBLIC.TYPES".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[TypesRow]] =
    sql"SELECT ID, NAME FROM PUBLIC.TYPES WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[TypesRow] =
    sql"SELECT ID, NAME FROM PUBLIC.TYPES WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[TypesRow]] =
    sql"SELECT ID, NAME FROM PUBLIC.TYPES WHERE $whereQuery".readRowOpt()
  def findById(id: TypesRow.PK): DbAction[TypesRow] = sql"SELECT ID, NAME FROM PUBLIC.TYPES WHERE ID = $id".readRow()
  def findByIdOpt(id: TypesRow.PK): DbAction[Option[TypesRow]] =
    sql"SELECT ID, NAME FROM PUBLIC.TYPES WHERE ID = $id".readRowOpt()
  def findByIds(ids: Set[TypesRow.PK]): DbAction[Seq[TypesRow]] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"SELECT ID, NAME FROM PUBLIC.TYPES WHERE ID IN ($idsExpr)".readRows()
  }
  def insert(row: TypesRow): DbAction[Int] = sql"""INSERT INTO PUBLIC.TYPES(ID, NAME) 
VALUES (
      ${row.ID},${row.NAME}    
)""".insert()
  def updateById(row: TypesRow): DbAction[Int] = sql"""UPDATE PUBLIC.TYPES 
SET NAME = ${row.NAME}
    WHERE ID = ${row.ID}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.TYPES WHERE $whereQuery".update()
  def deleteById(id: TypesRow.PK): DbAction[Int] = sql"DELETE FROM PUBLIC.TYPES WHERE ID = $id".update()
  def deleteByIds(ids: Set[TypesRow.PK]): DbAction[Int] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"DELETE FROM PUBLIC.TYPES WHERE ID IN ($idsExpr)".update()
  }
}
