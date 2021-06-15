package app

import io.jooby.Kooby
import io.jooby.ModelAndView
import io.jooby.runApp
import io.jooby.thymeleaf.ThymeleafModule

class App: Kooby({
    install(ThymeleafModule())

    get("") {
        ModelAndView("index.html")
            .put("scriptLocation", "/scripts/app.js")
    }
})

fun main(args: Array<String>) {
    runApp(args, App::class)
}