package com.example.petstore.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petstore.db.models.*
object SpecialtiesDao extends SpecialtiesDao
class SpecialtiesDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.SPECIALTIES".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM PUBLIC.SPECIALTIES WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[SpecialtiesRow]] = sql"SELECT ID, NAME FROM PUBLIC.SPECIALTIES".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[SpecialtiesRow]] =
    sql"SELECT ID, NAME FROM PUBLIC.SPECIALTIES WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[SpecialtiesRow] =
    sql"SELECT ID, NAME FROM PUBLIC.SPECIALTIES WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[SpecialtiesRow]] =
    sql"SELECT ID, NAME FROM PUBLIC.SPECIALTIES WHERE $whereQuery".readRowOpt()
  def findById(id: SpecialtiesRow.PK): DbAction[SpecialtiesRow] =
    sql"SELECT ID, NAME FROM PUBLIC.SPECIALTIES WHERE ID = $id".readRow()
  def findByIdOpt(id: SpecialtiesRow.PK): DbAction[Option[SpecialtiesRow]] =
    sql"SELECT ID, NAME FROM PUBLIC.SPECIALTIES WHERE ID = $id".readRowOpt()
  def findByIds(ids: Set[SpecialtiesRow.PK]): DbAction[Seq[SpecialtiesRow]] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"SELECT ID, NAME FROM PUBLIC.SPECIALTIES WHERE ID IN ($idsExpr)".readRows()
  }
  def insert(row: SpecialtiesRow): DbAction[Int] = sql"""INSERT INTO PUBLIC.SPECIALTIES(ID, NAME) 
VALUES (
      ${row.ID},${row.NAME}    
)""".insert()
  def updateById(row: SpecialtiesRow): DbAction[Int] = sql"""UPDATE PUBLIC.SPECIALTIES 
SET NAME = ${row.NAME}
    WHERE ID = ${row.ID}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.SPECIALTIES WHERE $whereQuery".update()
  def deleteById(id: SpecialtiesRow.PK): DbAction[Int] = sql"DELETE FROM PUBLIC.SPECIALTIES WHERE ID = $id".update()
  def deleteByIds(ids: Set[SpecialtiesRow.PK]): DbAction[Int] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"DELETE FROM PUBLIC.SPECIALTIES WHERE ID IN ($idsExpr)".update()
  }
}
