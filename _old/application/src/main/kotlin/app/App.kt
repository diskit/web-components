package app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jooby.*
import io.jooby.json.JacksonModule
import io.jooby.thymeleaf.ThymeleafModule

class App: Kooby({

  install(ThymeleafModule())
  install(JacksonModule(ObjectMapper().registerModule(KotlinModule())))

  val storage = TokenStorage()
  val config = ResourceConfiguration(
    environment.config.getString("lib.components.endpoint"),
    environment.config.getString("lib.components.accessKey"))
  val resource = ComponentResource(storage, config)

  val converter = Converter(
    environment.config.getString("lib.token.secret"),
    environment.config.getString("lib.token.issuer")
  )
  decorator(ResourceHandler(storage, converter))
  before(CredentialHandler(converter))
  mount(Index(resource))
  mount(System())
  mount(Api())
  mount(Auth(storage, converter))
  mount(SignIn(storage))
  assets("/resources/*", "statics")
})

class ResourceHandler(private val storage: TokenStorage, private val encoder: Encoder): Route.Decorator {

  override fun apply(next: Route.Handler): Route.Handler {
    return Route.Handler(fun(ctx: Context): Any {
      if (!ctx.requestPath.startsWith("/resources/scripts/"))
        return next.apply(ctx)
      val consumer = ctx.query("token").valueOrNull()
          ?.let(::Token)
          ?.let { storage.find(it) }
        ?: return ctx.send(StatusCode.UNAUTHORIZED)
      val token = encoder.encode(consumer)
      val result = next.apply(ctx)
      ctx.responseCode.takeIf { it == StatusCode.OK }
        ?.let { setJwt(ctx, token) }
      return result
    })
  }

  private fun setJwt(ctx: Context, token: String) {
    Cookie("t", token)
      .apply { isHttpOnly = true }
      .let { ctx.setResponseCookie(it) }
  }
}

class CredentialHandler(private val decoder: Decoder): Route.Before {
  override fun apply(ctx: Context) {
    if (!ctx.requestPath.startsWith("/api/"))
      return
    ctx.cookie("t").valueOrNull()
      ?.let { decoder.decode(it) }
      ?.let { ctx.setUser(it) }
      ?: ctx.send(StatusCode.UNAUTHORIZED)
  }

}

fun main(args: Array<String>) {
  runApp(args, App::class)
}

