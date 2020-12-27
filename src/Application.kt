package manlan

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.util.*
import org.mindrot.jbcrypt.BCrypt

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
fun Application.module() {
  install(ContentNegotiation) {
    jackson { }
  }


  install(Authentication) {
    jwt {
      realm = "com.manlan"
      verifier(JWtFactory.makeJWTVerifier())
      validate { jwtCredential ->
        val username = jwtCredential.payload.getClaim("username").asString()
        if (username != null) Reader(username) else null
      }
    }
  }


  routing {
    login()

    authenticate {
      getBooksAround()
    }
  }
}

suspend fun userNameExists(username: String): Reader? {
  return DbRepo.findUser(username)
}





