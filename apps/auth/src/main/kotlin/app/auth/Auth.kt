package app.auth

import io.jooby.Kooby
import io.jooby.Route
import io.jooby.body

class Auth: Kooby({
    path ("/auth") {
        post("") { ctx -> ctx.body<AuthRequestJson>() }
        delete("") {

        }
    }
})
class AuthRequestJson(val consumer: String, val user: String)

class Token: Kooby({
    path("/token") {
        post("") {

        }
    }
})

data class TokenJson(val token: String)