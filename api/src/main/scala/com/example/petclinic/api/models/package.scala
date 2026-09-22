package com.example.petclinic.api.models

import java.time.LocalDate
import ba.sake.tupson.JsonRW
import org.typelevel.jawn.ast.*

given JsonRW[LocalDate] with {
  override def write(value: LocalDate): JValue = JString(value.toString)

  override def parse(path: String, jValue: JValue): LocalDate =
    val str = JsonRW[String].parse(path, jValue)
    LocalDate.parse(str)
}
