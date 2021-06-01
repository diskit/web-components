package app

import io.jooby.Kooby
import io.jooby.ModelAndView

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

class Auth(val tokenStorage: TokenStorage): Kooby({
    path("/auth") {
        post("") {
            ctx.body(SignInJson::class.java)
                .let { Credential("sample-app", it.user)}
                .let { token -> Token.generate().also { tokenStorage.store(it, token)} }
                .let { TokenJson(it.value) }
        }
        delete("") {
            println("delete")
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