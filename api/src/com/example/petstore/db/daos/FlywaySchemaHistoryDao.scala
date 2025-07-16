package com.example.petstore.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
import com.example.petstore.db.models.*
object FlywaySchemaHistoryDao extends FlywaySchemaHistoryDao
class FlywaySchemaHistoryDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM PUBLIC.flyway_schema_history".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM PUBLIC.flyway_schema_history WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[FlywaySchemaHistoryRow]] =
    sql"SELECT installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success FROM PUBLIC.flyway_schema_history"
      .readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[FlywaySchemaHistoryRow]] =
    sql"SELECT installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success FROM PUBLIC.flyway_schema_history WHERE $whereQuery"
      .readRows()
  def findWhere(whereQuery: Query): DbAction[FlywaySchemaHistoryRow] =
    sql"SELECT installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success FROM PUBLIC.flyway_schema_history WHERE $whereQuery"
      .readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[FlywaySchemaHistoryRow]] =
    sql"SELECT installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success FROM PUBLIC.flyway_schema_history WHERE $whereQuery"
      .readRowOpt()
  def findById(id: FlywaySchemaHistoryRow.PK): DbAction[FlywaySchemaHistoryRow] =
    sql"SELECT installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success FROM PUBLIC.flyway_schema_history WHERE installed_rank = $id"
      .readRow()
  def findByIdOpt(id: FlywaySchemaHistoryRow.PK): DbAction[Option[FlywaySchemaHistoryRow]] =
    sql"SELECT installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success FROM PUBLIC.flyway_schema_history WHERE installed_rank = $id"
      .readRowOpt()
  def findByIds(ids: Set[FlywaySchemaHistoryRow.PK]): DbAction[Seq[FlywaySchemaHistoryRow]] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"SELECT installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success FROM PUBLIC.flyway_schema_history WHERE installed_rank IN ($idsExpr)"
      .readRows()
  }
  def insert(row: FlywaySchemaHistoryRow): DbAction[Int] =
    sql"""INSERT INTO PUBLIC.flyway_schema_history(installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) 
VALUES (
      ${row.installed_rank},${row.version},${row.description},${row.`type`},${row.script},${row.checksum},${row.installed_by},${row.installed_on},${row.execution_time},${row.success}    
)""".insert()
  def updateById(row: FlywaySchemaHistoryRow): DbAction[Int] = sql"""UPDATE PUBLIC.flyway_schema_history 
SET version = ${row.version}, description = ${row.description}, type = ${row.`type`}, script = ${row.script}, checksum = ${row.checksum}, installed_by = ${row.installed_by}, installed_on = ${row.installed_on}, execution_time = ${row.execution_time}, success = ${row.success}
    WHERE installed_rank = ${row.installed_rank}""".update()
  def deleteWhere(whereQuery: Query): DbAction[Int] =
    sql"DELETE FROM PUBLIC.flyway_schema_history WHERE $whereQuery".update()
  def deleteById(id: FlywaySchemaHistoryRow.PK): DbAction[Int] =
    sql"DELETE FROM PUBLIC.flyway_schema_history WHERE installed_rank = $id".update()
  def deleteByIds(ids: Set[FlywaySchemaHistoryRow.PK]): DbAction[Int] = {
    val idsExpr = ids.map(id => sql"${id}").reduce(_ ++ (sql",") ++ _)
    sql"DELETE FROM PUBLIC.flyway_schema_history WHERE installed_rank IN ($idsExpr)".update()
  }
}
