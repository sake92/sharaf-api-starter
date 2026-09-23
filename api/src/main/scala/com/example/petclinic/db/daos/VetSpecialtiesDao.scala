package com.example.petclinic.db.daos
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
import com.example.petclinic.db.models.*
object VetSpecialtiesDao extends VetSpecialtiesDao
class VetSpecialtiesDao {
  def countAll(): DbAction[Int] = sql"SELECT COUNT(*) FROM public.vet_specialties".readValue()
  def countWhere(whereQuery: Query): DbAction[Int] =
    sql"SELECT COUNT(*) FROM public.vet_specialties WHERE $whereQuery".readValue()
  def findAll(): DbAction[Seq[VetSpecialtiesRow]] =
    sql"SELECT vet_id, specialty_id FROM public.vet_specialties".readRows()
  def findAllWhere(whereQuery: Query): DbAction[Seq[VetSpecialtiesRow]] =
    sql"SELECT vet_id, specialty_id FROM public.vet_specialties WHERE $whereQuery".readRows()
  def findWhere(whereQuery: Query): DbAction[VetSpecialtiesRow] =
    sql"SELECT vet_id, specialty_id FROM public.vet_specialties WHERE $whereQuery".readRow()
  def findWhereOpt(whereQuery: Query): DbAction[Option[VetSpecialtiesRow]] =
    sql"SELECT vet_id, specialty_id FROM public.vet_specialties WHERE $whereQuery".readRowOpt()
  def insert(row: VetSpecialtiesRow): DbAction[VetSpecialtiesRow] =
    sql"""INSERT INTO public.vet_specialties(vet_id, specialty_id)
    VALUES (
      ${row.vet_id},${row.specialty_id}
    )
    RETURNING vet_id, specialty_id""".insertReturningRow()
  def deleteWhere(whereQuery: Query): DbAction[Int] = sql"DELETE FROM public.vet_specialties WHERE $whereQuery".update()
}
