package com.example.petstore.main

import io.undertow.Undertow
import ba.sake.sharaf.*
import com.example.petstore.api.controllers.*
import com.example.petstore.ui.controllers.SwaggerUIController

@main def apiMain =
  val allRoutes = Seq[Routes](
    new UserController().routes,
    new PetController().routes,
    new StoreController().routes,
    new SwaggerUIController().routes
  )
  Undertow.builder
    .addHttpListener(8080, "localhost")
    .setHandler(SharafHandler(Routes.merge(allRoutes)))
    .build
    .start()
  println("Server started at http://localhost:8080")
