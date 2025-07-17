package com.example.petstore.main

import ba.sake.sharaf.*
import ba.sake.sharaf.undertow.UndertowSharafServer
import com.example.petstore.api.controllers.*
import com.example.petstore.ui.controllers.SwaggerUIController
import java.nio.file.Paths
import ba.sake.squery.SqueryContext
import org.h2.jdbcx.JdbcDataSource

@main def apiMain =
  val ds = JdbcDataSource()
  ds.setURL(sys.env("JDBC_URL"))
  val dbCtx = SqueryContext(ds)
  val routes = Routes.merge(
    Seq(
      UserController().routes,
      PetController(dbCtx).routes,
      StoreController().routes,
      SwaggerUIController().routes
    )
  )
  UndertowSharafServer("localhost", 8080, routes).start()
  println("Server started at http://localhost:8080")
