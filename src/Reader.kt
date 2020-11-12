package manlan

data class Reader(
    val username : String = "",
    val email : String = "",
    val address : Address,
    val password: String
)