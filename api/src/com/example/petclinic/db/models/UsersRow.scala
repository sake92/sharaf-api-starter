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
  inline val firstName = "FIRST_NAME"
  inline val allCols = "USERNAME, PASSWORD, ENABLED, FIRST_NAME"
  transparent inline def allColsWithPrefix(inline prefix: String) = {
    prefix + ".USERNAME" + "," + (prefix + ".PASSWORD") + "," + (prefix + ".ENABLED") + "," + (prefix + ".FIRST_NAME")
  }
  type PK = String
}
case class UsersRow(USERNAME: String, PASSWORD: String, ENABLED: Boolean, FIRST_NAME: Option[String]) derives SqlReadRow {
  def pk: UsersRow.PK = USERNAME
}
