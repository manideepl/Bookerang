package manlan

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JWtFactory {

    private const val secret = "secret"
    private const val issuer = "com.manlan"
    private const val validity = 36_000_000 * 24
    private val alogirthm: Algorithm = Algorithm.HMAC256(secret)

    fun generateToken(username : String): String {
        return JWT.create()
            .withIssuer(issuer)
            .withClaim("username", username)
            .withExpiresAt(getExpiration())
            .sign(alogirthm)
    }

    fun makeJWTVerifier() : JWTVerifier {
        return JWT.require(alogirthm).withIssuer(issuer).build()
    }

    private fun getExpiration() = Date(System.currentTimeMillis() + validity)
}
