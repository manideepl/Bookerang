package manlan

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.getBooksAround() {
  get("/books") {
    val input: InputForBooks = call.receive()
    call.respond(DbRepo.getReaders(input.radius, input.geoLocation))
  }
}
