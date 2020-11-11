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

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        jackson { }
    }

    install(Authentication) {
        jwt {
            realm = "jwt.realm"
            verifier(makeJWTVerifier("jwt.domain", "jwt.audience"))
            validate { jwtCredential ->
                if (jwtCredential.payload.audience.contains("jwt.audience")) JWTPrincipal(jwtCredential.payload) else null
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
            
        }
    }
}

fun makeJWTVerifier(issuer: String, audience: String): JWTVerifier {
    return JWT.require(Algorithm.HMAC256("secret")).withAudience(audience).withIssuer(issuer).build()
}

