package manlan

import org.mindrot.jbcrypt.BCrypt

fun authenticateReader(reader: Reader, input: InputForLogin): String {
  val pwd = reader.password

  if (BCrypt.checkpw(input.password, pwd)) {
    val token = JWtFactory.generateToken(reader.username)
    return "jwt: $token"
  }
  return "incorrect password"
}
