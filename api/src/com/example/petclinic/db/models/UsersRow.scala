package com.example.petclinic.db.models
import java.time.*
import java.util.UUID
import ba.sake.squery.{*, given}
import ba.sake.squery.read.{*, given}
import ba.sake.squery.write.{*, given}
import ba.sake.squery.h2.{*, given}
object UsersRow {
  inline val tableName = "PUBLIC.USERS"
  inline val username = "USERNAME"
  inline val password = "PASSWORD"
  inline val enabled = "ENABLED"
  inline val allCols = "USERNAME, PASSWORD, ENABLED"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".USERNAME" + "," + (prefix + ".PASSWORD") + "," + (prefix + ".ENABLED")
  }
  type PK = String
}
case class UsersRow(USERNAME: String, PASSWORD: String, ENABLED: Boolean) derives SqlReadRow {
  def pk: UsersRow.PK = USERNAME
}
