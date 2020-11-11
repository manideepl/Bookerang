package manlan

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    routing {
        get("/") {
            val msg = DbRepo.getDBVersion()
            call.respondText(msg)
        }
    }
}

