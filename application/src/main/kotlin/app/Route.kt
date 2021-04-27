package app

import io.jooby.Kooby

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