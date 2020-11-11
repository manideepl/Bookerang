package manlan

import com.datastax.oss.driver.api.core.CqlSession
import java.nio.file.Paths

object DbRepo {
//    TODO move the parameters to application.conf
    private val session by lazy {   CqlSession.builder()
        .withCloudSecureConnectBundle(Paths.get("C:\\Users\\00001889\\Desktop\\secure-connect-bookerangdb.zip"))
        .withAuthCredentials("learner", "learner")
        .build() }


    fun getDBVersion(): String {
        val resultSet = session.execute("SELECT release_version FROM system.local")
        val row = resultSet.one()


        return row?.getString("release_version") ?: "an error occurred"
    }
}