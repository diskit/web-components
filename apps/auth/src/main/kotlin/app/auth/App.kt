import app.auth.Auth
import app.auth.Token
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.jooby.Kooby
import io.jooby.json.JacksonModule
import io.jooby.runApp

class App: Kooby({

    install(JacksonModule(ObjectMapper().registerModule(KotlinModule())))

    get("/systems/ping") {
        "pong"
    }
    mount(Token())
    mount(Auth())
})

fun main(args: Array<String>) {
    runApp(args, App::class)
}

