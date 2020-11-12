package manlan

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.slf4j.LoggerFactory

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val logger = LoggerFactory.getLogger(Application::class.java)

fun Application.module() {
    install(ContentNegotiation) {
        jackson { }
    }

    install(Authentication) {
        jwt {
            realm = "jwt.realm"
            verifier(makeJWTVerifier("jwt.domain", "jwt.audience"))
            validate { jwtCredential ->
                if (userNameExists(jwtCredential.payload.getClaim("username").asString()) != null) JWTPrincipal(
                    jwtCredential.payload
                ) else null
            }
        }
    }

    routing {
        authenticate {
            get("/books") {
                val input: InputForBooks = call.receive()
                call.respond(DbRepo.getReaders(input.radius, input.geoLocation))
            }
        }

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
}

suspend fun userNameExists(username: String): Reader? {
    return DbRepo.findUser(username)
}

fun authenticateReader(reader: Reader, input: InputForLogin): String {
    val pwd = reader.password
//    TODO replace with bcrypt
    if (pwd == input.password) {
        val token = JWtFactory.generateToken(input.username)
        logger.debug("token: $token")
        return "jwt: $token"
    }

    return "Incorrect password"
}

fun makeJWTVerifier(issuer: String, audience: String): JWTVerifier {
    return JWT.require(Algorithm.HMAC256("secret")).withAudience(audience).withIssuer(issuer).build()
}

