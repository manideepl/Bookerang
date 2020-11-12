package manlan

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JWtFactory {

    fun generateToken(username : String): String {
        return JWT.create().withSubject("Authentication")
            .withIssuer("jwt.domain")
            .withClaim("username", username)
            .withExpiresAt(getExpiration()).sign(Algorithm.HMAC256("secret"))
    }

    private fun getExpiration() = Date(System.currentTimeMillis() + 36_000_000)
}