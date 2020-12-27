package manlan

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.login() {
  post("/login") {
    val input = call.receive<InputForLogin>()
    val found = userNameExists(input.username)

    if (found != null) {
      val result = authenticateReader(found, input)
      call.respondText { result }
    } else {
      call.respondText { "User not found!" }
    }
  }
}
