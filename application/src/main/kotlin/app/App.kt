package app

import io.jooby.*
import io.jooby.thymeleaf.ThymeleafModule

class App: Kooby({

  install(ThymeleafModule())
  val storage = TokenStorage()

  before(TokenValidator(storage))
  mount(Index(storage))
  mount(System())
  assets("/resources/*", "statics")
})

class TokenValidator(private val storage: TokenStorage): Route.Before {

  override fun apply(ctx: Context) {
    ctx.requestPath.takeIf { it.startsWith("/resources/") }
      ?.let { ctx.query("token").valueOrNull() }
      ?.let(::Token)
      ?.takeUnless(storage::contains)
      ?.let { ctx.send(StatusCode.UNAUTHORIZED) }
  }
}


fun main(args: Array<String>) {
  runApp(args, App::class)
}

