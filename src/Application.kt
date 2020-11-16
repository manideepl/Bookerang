package manlan

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)


@KtorExperimentalAPI
fun Application.module() {
    install(ContentNegotiation) {
        jackson { }
    }


    routing {
        get("/") {
            call.respondText { "Hello my lady!" }
        }
    }
}
