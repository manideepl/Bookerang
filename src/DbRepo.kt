package manlan

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


object DbRepo {
    //    TODO move the parameters to application.conf

    private val client by lazy {
        val connectionString = "mongodb+srv://learner:learner@cluster0.rtcig.mongodb.net/bookerangdb?retryWrites=true&w=majority"
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
}