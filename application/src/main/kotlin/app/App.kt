package app

import io.jooby.Kooby
import io.jooby.runApp
import io.jooby.thymeleaf.ThymeleafModule

class App: Kooby({

  install(ThymeleafModule())

  get("/") {
    "Welcome to Jooby!"
  }
})

fun main(args: Array<String>) {
  runApp(args, App::class)
}
