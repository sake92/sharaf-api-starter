package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.postgres.{*, given}
object UsersRow {
  inline val tableName = "public.users"
  inline val username = "username"
  inline val password = "password"
  inline val enabled = "enabled"
  inline val allCols = "username, password, enabled"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".username" + "," + (prefix + ".password") + "," + (prefix + ".enabled")
  }
  type PK = String
}
case class UsersRow(username: String, password: String, enabled: Boolean) derives SqlReadRow {
  def pk: UsersRow.PK = username
}
