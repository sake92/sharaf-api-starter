package com.example.petclinic.api.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class User(username: String, password: Option[String], enabled: Option[Boolean], firstName: Option[String], roles: Option[Seq[Role]])
    derives JsonRW
object User { given Validator[User] = Validator.derived[User].minLength(_.username, 1).maxLength(_.username, 80) }
