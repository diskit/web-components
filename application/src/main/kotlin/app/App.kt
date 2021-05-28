package app

import io.jooby.*
import io.jooby.thymeleaf.ThymeleafModule


class TokenStore {
  private val values: MutableSet<String> = mutableSetOf("preset")

  fun add(token: String) {
    values.add(token)
  }

  fun contains(token: String?) = values.contains(token)
}

val store = TokenStore()

class App: Kooby({

  install(ThymeleafModule())
  val storage = TokenStorage()


  before {
    ctx.requestPath.takeIf { it.startsWith("/resources/") }
      ?.takeUnless { store.contains(ctx.query("token").valueOrNull()) }
      ?.let { ctx.send(StatusCode.UNAUTHORIZED) }
  }

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

