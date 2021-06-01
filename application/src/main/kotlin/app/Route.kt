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

class Index(private val resource: ComponentResource): Kooby({
    get("/") {

        ModelAndView("index.html")
            .put("scriptLocation", resource.path(Credential("sample-app", "100223")))
            .put("styleLocation", environment.config.getString("lib.style.endpoint"))
            .put("name", "hoge")
    }
})