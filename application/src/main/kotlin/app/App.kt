package app

import io.jooby.Kooby
import io.jooby.ModelAndView
import io.jooby.runApp
import io.jooby.thymeleaf.ThymeleafModule

class App: Kooby({

  install(ThymeleafModule())

  get("/") {
    val scriptLocation =
      "${environment.config.getString("lib.components.endpoint")}?${environment.config.getString("lib.components.accessKey")}=token"


    ModelAndView("index.html")
      .put("scriptLocation", scriptLocation)
      .put("styleLocation", environment.config.getString("lib.style.endpoint"))
      .put("name", "hoge")
  }
  mount(System())
  assets("/resources/*", "statics")
})

fun main(args: Array<String>) {
  runApp(args, App::class)
}
