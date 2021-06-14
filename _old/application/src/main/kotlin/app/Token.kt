package app

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class TokenStorage {

    private val storage: MutableMap<Token, Credential> = mutableMapOf()

    fun contains(token: Token) = storage.contains(token)

    fun store(token: Token, credential: Credential) =
        storage.putIfAbsent(token, credential)

    fun find(token: Token): Credential? = storage[token]
}

data class Token(val value: String) {

    companion object {
        fun generate() = UUID.randomUUID().toString().let(::Token)
    }
}

data class Credential(val app: String, val user: String?) {
    companion object {
        fun anonymous(app: String): Credential {
            return Credential(app, null)
        }
    }
}

interface Encoder {
    fun encode(credential: Credential): String
}
interface Decoder {
    fun decode(value: String): Credential?
}

class Converter(secret: String, private val issuer: String): Decoder, Encoder {
    private val algorithm = Algorithm.HMAC256(secret)
    private val verifier = JWT.require(algorithm).withIssuer(issuer).build()

    override fun encode(credential: Credential): String {
        val withClaim = JWT.create()
            .withIssuer(issuer)
            .withClaim("consumer", credential.app)
        credential.user?.let { withClaim.withClaim("user", it) }
        return withClaim.sign(algorithm)
    }


    override fun decode(value: String): Credential? =
        try {
            verifier.verify(value).claims
                .let { Credential(it["consumer"]!!.asString(), it["user"]?.asString()) }
        } catch (e: Exception) {
            null
        }
}

fun main() {
    val v =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzYW1wbGUtYXBwIiwidXNlciI6IjEyMzQ1IiwiY29uc3VtZXIiOiJzYW1wbGUtYXBwIn0.V6Q4jG5HGZU2Vi1Rr2q5eNcHht8YiURMdiEoQgv1pBU"
    println(Converter("eijozjijfexceseijopzojveescsds", "sample-app").decode(v))
}