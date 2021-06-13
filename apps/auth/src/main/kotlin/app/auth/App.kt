package app.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jooby.Kooby
import io.jooby.json.JacksonModule
import io.jooby.runApp
import java.util.*

class App: Kooby({

    install(JacksonModule(ObjectMapper().registerModule(KotlinModule())))

    get("/systems/ping") {
        "pong"
    }
    val storage = TokenStorage()
    val converter = Converter(UUID.randomUUID().toString(), "sample")
    mount(TokenRouter(storage))
    mount(CredentialRouter(storage, converter))
    mount(Auth())
})

fun main(args: Array<String>) {
    runApp(args, App::class)
}

