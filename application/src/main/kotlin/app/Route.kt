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
        path("/v1") {
            get("/") {

            }
        }
    }
})

class Index(private val storage: TokenStorage): Kooby({
    get("/") {
        val scriptLocation = Token.generate()
            .also(storage::store)
            .let { "${environment.config.getString("lib.components.endpoint")}?${environment.config.getString("lib.components.accessKey")}=${it.value}" }

        ModelAndView("index.html")
            .put("scriptLocation", scriptLocation)
            .put("styleLocation", environment.config.getString("lib.style.endpoint"))
            .put("name", "hoge")
    }
})