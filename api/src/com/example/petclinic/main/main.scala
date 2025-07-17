package com.example.petclinic.main

import org.h2.jdbcx.JdbcDataSource
import ba.sake.squery.SqueryContext
import ba.sake.sharaf.*
import ba.sake.sharaf.undertow.UndertowSharafServer
import com.example.petclinic.api.controllers.*
import com.example.petclinic.ui.controllers.SwaggerUIController

@main def apiMain =
  val ds = JdbcDataSource()
  ds.setURL(sys.env("JDBC_URL"))
  val dbCtx = SqueryContext(ds)
  val routes = Routes.merge(
    Seq(
      FailingController().routes,
      OwnerController().routes,
      PetController(dbCtx).routes,
      PettypesController(dbCtx).routes,
      SpecialtyController().routes,
      UserController().routes,
      VetController().routes,
      VisitController().routes,
      SwaggerUIController().routes
    )
  )
  UndertowSharafServer("localhost", 8080, routes).start()
  println("Server started at http://localhost:8080")
