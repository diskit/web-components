package app

import io.jooby.Kooby
import io.jooby.ModelAndView
import io.jooby.TraceHandler
import io.jooby.runApp
import io.jooby.thymeleaf.ThymeleafModule

class App: Kooby({

  install(ThymeleafModule())
  val storage = TokenStorage()

  get("/") {
    val token = Token.generate()
    storage.store(token)
    val scriptLocation =
      "${environment.config.getString("lib.components.endpoint")}?${environment.config.getString("lib.components.accessKey")}=${token.value}"

    ModelAndView("index.html")
      .put("scriptLocation", scriptLocation)
      .put("styleLocation", environment.config.getString("lib.style.endpoint"))
      .put("name", "hoge")
  }
  mount(System())
  assets("/resources/*", "statics")
  decorator(TraceHandler())
})

fun main(args: Array<String>) {
  runApp(args, App::class)
}
