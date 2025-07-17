package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petclinic.db.models.*
object PetsDao extends PetsDao
class PetsDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.PETS".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.PETS WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[PetsRow]] =
    sql"SELECT ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID FROM PUBLIC.PETS".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[PetsRow]] =
    sql"SELECT ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID FROM PUBLIC.PETS WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[PetsRow] =
    sql"SELECT ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID FROM PUBLIC.PETS WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[PetsRow]] =
    sql"SELECT ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID FROM PUBLIC.PETS WHERE $whereQuery".readRowOpt()
  def findById(id: PetsRow.PK): DbAction[PetsRow] =
    sql"SELECT ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID FROM PUBLIC.PETS WHERE ID = $id".readRow()
  def findByIdOpt(id: PetsRow.PK): DbAction[Option[PetsRow]] =
    sql"SELECT ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID FROM PUBLIC.PETS WHERE ID = $id".readRowOpt()
  def findByIds(ids: Set[PetsRow.PK]): DbAction[Seq[PetsRow]] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"SELECT ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID FROM PUBLIC.PETS WHERE ID IN ($idsExpr)".readRows()
  }
  def insert(row: PetsRow): DbAction[Int] = sql"""INSERT INTO PUBLIC.PETS(ID, NAME, BIRTH_DATE, TYPE_ID, OWNER_ID) 
VALUES (
      ${row.ID},${row.NAME},${row.BIRTH_DATE},${row.TYPE_ID},${row.OWNER_ID}    
)""".insert()
  def updateById(row: PetsRow): DbAction[Int] = sql"""UPDATE PUBLIC.PETS 
SET NAME = ${row.NAME}, BIRTH_DATE = ${row.BIRTH_DATE}, TYPE_ID = ${row.TYPE_ID}, OWNER_ID = ${row.OWNER_ID}
    WHERE ID = ${row.ID}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.PETS WHERE $whereQuery".update()
  def deleteById(id: PetsRow.PK): DbAction[Int] = sql"DELETE FROM PUBLIC.PETS WHERE ID = $id".update()
  def deleteByIds(ids: Set[PetsRow.PK]): DbAction[Int] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"DELETE FROM PUBLIC.PETS WHERE ID IN ($idsExpr)".update()
  }
}
