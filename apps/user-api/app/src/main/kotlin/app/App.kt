package app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jooby.Kooby
import io.jooby.json.JacksonModule
import io.jooby.runApp


class App: Kooby({
    install(JacksonModule(ObjectMapper().registerModule(KotlinModule())))

    path("/users") {
        get("/{id}") {
            val id = ctx.path("id").value()
            UserResponseJson(id, "name")
        }
    }

    path("/systems") {
        get("/ping") {
            "pong"
        }
    }

})

data class UserResponseJson(val id: String, val name:String)

fun main(args: Array<String>) {
    runApp(args, App::class)
}