package manlan

import io.ktor.auth.*

data class Reader(
    val username : String = "",
    val email : String = "",
    val address : Address? = null,
    val password: String = ""
) : Principal