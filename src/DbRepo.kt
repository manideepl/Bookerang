package manlan

import com.typesafe.config.ConfigFactory
import org.bson.Document
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


object DbRepo {
    private val client by lazy {
        val config = ConfigFactory.load()
        val connectionString = config.getString("mongo.connectionString")
        KMongo.createClient(connectionString).coroutine

    }

    private val db by lazy {
        client.getDatabase("bookerangdb")
    }

    suspend fun getReaders(radius: Double, geoLocation: GeoLocation): List<Reader> {
        val geoNearDoc = Document()
        val nearDoc = Document()
        nearDoc["type"] = "Point"
        val coordinates = doubleArrayOf(geoLocation.latitude, geoLocation.longitude)
        nearDoc["coordinates"] = coordinates
        geoNearDoc["near"] = nearDoc
        geoNearDoc["distanceField"] = "dist.calculated"
        geoNearDoc["maxDistance"] = radius

        val topDoc = Document()
        topDoc["\$geoNear"] = geoNearDoc

        val collection = db.getCollection<Reader>("readers")
        return collection.aggregate<Reader>(listOf(topDoc)).toList()
    }

    suspend fun findUser(username: String): Reader? {
        val collection = db.getCollection<Reader>("readers")

        val filter = Document()
        filter["username"] = username
        return collection.findOne(filter)
    }
}