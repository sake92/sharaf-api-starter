package com.example.petstore.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petstore.db.models.*
object VisitsDao extends VisitsDao
class VisitsDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.VISITS".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM PUBLIC.VISITS WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[VisitsRow]] =
    sql"SELECT ID, PET_ID, VISIT_DATE, DESCRIPTION FROM PUBLIC.VISITS".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[VisitsRow]] =
    sql"SELECT ID, PET_ID, VISIT_DATE, DESCRIPTION FROM PUBLIC.VISITS WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[VisitsRow] =
    sql"SELECT ID, PET_ID, VISIT_DATE, DESCRIPTION FROM PUBLIC.VISITS WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[VisitsRow]] =
    sql"SELECT ID, PET_ID, VISIT_DATE, DESCRIPTION FROM PUBLIC.VISITS WHERE $whereQuery".readRowOpt()
  def findById(id: VisitsRow.PK): DbAction[VisitsRow] =
    sql"SELECT ID, PET_ID, VISIT_DATE, DESCRIPTION FROM PUBLIC.VISITS WHERE ID = $id".readRow()
  def findByIdOpt(id: VisitsRow.PK): DbAction[Option[VisitsRow]] =
    sql"SELECT ID, PET_ID, VISIT_DATE, DESCRIPTION FROM PUBLIC.VISITS WHERE ID = $id".readRowOpt()
  def findByIds(ids: Set[VisitsRow.PK]): DbAction[Seq[VisitsRow]] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"SELECT ID, PET_ID, VISIT_DATE, DESCRIPTION FROM PUBLIC.VISITS WHERE ID IN ($idsExpr)".readRows()
  }
  def insert(row: VisitsRow): DbAction[Int] = sql"""INSERT INTO PUBLIC.VISITS(ID, PET_ID, VISIT_DATE, DESCRIPTION) 
VALUES (
      ${row.ID},${row.PET_ID},${row.VISIT_DATE},${row.DESCRIPTION}    
)""".insert()
  def updateById(row: VisitsRow): DbAction[Int] = sql"""UPDATE PUBLIC.VISITS 
SET PET_ID = ${row.PET_ID}, VISIT_DATE = ${row.VISIT_DATE}, DESCRIPTION = ${row.DESCRIPTION}
    WHERE ID = ${row.ID}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM PUBLIC.VISITS WHERE $whereQuery".update()
  def deleteById(id: VisitsRow.PK): DbAction[Int] = sql"DELETE FROM PUBLIC.VISITS WHERE ID = $id".update()
  def deleteByIds(ids: Set[VisitsRow.PK]): DbAction[Int] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"DELETE FROM PUBLIC.VISITS WHERE ID IN ($idsExpr)".update()
  }
}
