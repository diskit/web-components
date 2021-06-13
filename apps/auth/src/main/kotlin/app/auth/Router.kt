package app.auth

import io.jooby.*
import io.jooby.exception.NotFoundException
import java.util.*

class Auth(private val decoder: Decoder): Kooby({
    path ("/authn") {
        post("") {
            ctx.body<CredentialJson>()
                .let { decoder.decode(it.credential) }
                ?.let { UserJson(it.consumer, it.user) }
                ?: throw NotFoundException(ctx.contextPath)
        }
        delete("") {

        }
    }
})

data class Credential(val consumer: String, val user: String?)

class TokenStorage {
    private val storage: MutableMap<Token, Credential> = mutableMapOf()

    fun store(key: Token, value: Credential) = storage.put(key, value)
    fun find(key: Token) = storage[key]
}

data class Token(val value: String) {
    companion object {
        fun generate(): Token = UUID.randomUUID().toString().let(::Token)
    }
}

class TokenRequestJson(val consumer: String, val user: String?)

class TokenRouter(private val storage: TokenStorage): Kooby({
    path("/token") {
        post("") {
            ctx.body<TokenRequestJson>()
                .let { Credential(it.consumer, it.user) }
                .let { c -> Token.generate().also { storage.store(it, c) } }
                .let { TokenJson(it.value) }
        }
    }
})

class CredentialRouter(private val storage: TokenStorage, private val encoder: Encoder): Kooby({
    path("/credentials") {
        get("/{token}") {
            ctx.path("token").value()
                .let { Token(it) }
                .let { storage.find(it) }
                ?.let { encoder.encode(it) }
                ?.let { CredentialJson(it) }
                ?: throw NotFoundException(ctx.contextPath)
        }
    }
})

data class CredentialJson(val credential: String)
data class UserJson(val consumer: String, val user: String?)

data class TokenJson(val token: String)