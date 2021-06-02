package app

import io.jooby.*

class System: Kooby({
    path("/systems") {
        get("/ping") {
            "pong"
        }
    }
})

class Api: Kooby({
    path("/api") {
        get("/me") {
            val user = ctx.getUser<Credential>()!!
            user
        }
    }
})

class SignIn(private val tokenStorage: TokenStorage): Kooby({
    path("/signIn") {
        post("") {
            ctx.body(SignInJson::class.java)
                .let { Credential("sample-app", it.user)}
                .let { token -> Token.generate().also { tokenStorage.store(it, token)} }
                .let { TokenJson(it.value) }
        }
    }
    path("/signOut") {
        post("") {
            println("signOut")
        }
    }
})

class Auth(private val storage: TokenStorage, private val encoder: Encoder): Kooby({
  path("/auth") {
      post("") {
          ctx.body(TokenJson::class.java).token
              .let { storage.find(Token(it)) }
              ?.let { encoder.encode(it) }
              ?.let { Cookie("t", it) }
              ?.also { it.isHttpOnly = true }
              ?.let { ctx.setResponseCookie(it) }
              ?.let { emptyMap<String, Any>() }
              ?: ctx.send(StatusCode.BAD_REQUEST)
      }
      delete("") {
          Cookie("t", encoder.encode(Credential.anonymous("sample-app")))
              .also { it.isHttpOnly = true }
              .let { ctx.setResponseCookie(it) }
              .let { emptyMap<String, Any>() }
      }
  }
})

data class SignInJson(val user: String)

data class TokenJson(val token: String)

class Index(private val resource: ComponentResource): Kooby({
    get("/") {

        ModelAndView("index.html")
            .put("scriptLocation", resource.path(Credential.anonymous("sample-app")))
            .put("styleLocation", environment.config.getString("lib.style.endpoint"))
            .put("name", "hoge")
    }
})