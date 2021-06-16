package app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.jooby.Kooby
import io.jooby.ModelAndView
import io.jooby.json.JacksonModule
import io.jooby.runApp
import io.jooby.thymeleaf.ThymeleafModule
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.Charset

class App: Kooby({
    install(JacksonModule(ObjectMapper().registerModule(KotlinModule())))

    path("/users") {
        get("/{id}") {
            val id = ctx.path("id").value()
            UserResponseJson(id, "name")
        }
    }
})

data class UserResponseJson(val id: String, val name:String)

fun main(args: Array<String>) {
    runApp(args, App::class)
}