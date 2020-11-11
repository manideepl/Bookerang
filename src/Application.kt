package manlan

import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.slf4j.LoggerFactory

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        jackson {  }
    }


    routing {
            get("/books") {
                val input: InputForBooks = call.receive()
                call.respond(DbRepo.getReaders(input.radius, input.geoLocation))
            }
    }
}

