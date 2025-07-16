package com.example.petstore.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petstore.db.models.*
object VetSpecialtiesDao extends VetSpecialtiesDao
class VetSpecialtiesDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.VET_SPECIALTIES".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM PUBLIC.VET_SPECIALTIES WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[VetSpecialtiesRow]] =
    sql"SELECT VET_ID, SPECIALTY_ID FROM PUBLIC.VET_SPECIALTIES".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[VetSpecialtiesRow]] =
    sql"SELECT VET_ID, SPECIALTY_ID FROM PUBLIC.VET_SPECIALTIES WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[VetSpecialtiesRow] =
    sql"SELECT VET_ID, SPECIALTY_ID FROM PUBLIC.VET_SPECIALTIES WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[VetSpecialtiesRow]] =
    sql"SELECT VET_ID, SPECIALTY_ID FROM PUBLIC.VET_SPECIALTIES WHERE $whereQuery".readRowOpt()
  def insert(row: VetSpecialtiesRow): DbAction[Int] = sql"""INSERT INTO PUBLIC.VET_SPECIALTIES(VET_ID, SPECIALTY_ID) 
VALUES (
      ${row.VET_ID},${row.SPECIALTY_ID}    
)""".insert()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.VET_SPECIALTIES WHERE $whereQuery".update()
}
