package app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.jooby.Kooby
import io.jooby.ModelAndView
import io.jooby.runApp
import io.jooby.thymeleaf.ThymeleafModule
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.Charset

class App: Kooby({
    install(ThymeleafModule())

    val httpClient = HttpClient.newBuilder().build()
    val mapper = ObjectMapper().registerModule(KotlinModule())
    val authEndpoint = environment.config.getString("auth.endpoint")
    val resourceEndpoint = environment.config.getString("resource.endpoint")

    get("") {
        val token = HttpRequest.newBuilder(URI.create("$authEndpoint/token"))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(TokenRequestJson.anonymous()), Charset.forName("UTF-8")))
            .header("content-type", "application/json")
            .build()
            .let { httpClient.send(it, HttpResponse.BodyHandlers.ofString()) }
            .let { it.body() }
            .let { mapper.readValue<TokenResponseJson>(it) }
            .also { println(it) }

        ModelAndView("index.html")
            .put("scriptLocation", "$resourceEndpoint?token=${token.token}")
    }
})

data class TokenRequestJson(val consumer: String, val user: String?) {
    companion object {
        fun anonymous(): TokenRequestJson {
            return TokenRequestJson("sample-app", null)
        }
    }
}
data class TokenResponseJson(val token: String)

fun main(args: Array<String>) {
    runApp(args, App::class)
}