package com.example.petclinic.client.models
import java.time.*
import java.util.UUID
import org.typelevel.jawn.ast.JValue
import ba.sake.tupson.*
import ba.sake.validson.Validator
case class ValidationMessage(message: String) derives JsonRW
