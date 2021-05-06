import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.QueryDocumentSnapshot
import com.google.cloud.firestore.SetOptions
import com.google.cloud.firestore.WriteResult
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream
import java.io.InputStream

data class CityKt(val name: String, val region: String, val people: Int, val municipalities: List<String>)

class MyFirestoreKt(projectId: String) {
    private val fs: Firestore

    init {
        this.fs = init(projectId)
    }

    private fun init(projectId: String): Firestore {
        val serviceAccount: InputStream =
            FileInputStream("src/main/resources/serviceAccountKey.json")
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setProjectId(projectId)
            .build()
        FirebaseApp.initializeApp(options)
        return FirestoreClient.getFirestore()
    }

    fun setData(collection: String, document: String, city: CityKt, merge: Boolean): WriteResult =
        if (merge) {
            fs.collection(collection).document(document).set(city, SetOptions.merge()).get()
        } else {
            fs.collection(collection).document(document).set(city).get()
        }

    fun setData(collection: String, document: String, values: Map<String, Any>, merge: Boolean): WriteResult =
        if (merge) {
            fs.collection(collection).document(document).set(values, SetOptions.merge()).get()
        } else {
            fs.collection(collection).document(document).set(values).get()
        }

    fun updateData(collection: String, document: String, updateValues: Map<String, Any>): WriteResult =
        fs.collection(collection).document(document).update(updateValues).get()

    fun close() = fs.close()

    fun readAllData(collection: String): List<QueryDocumentSnapshot> =
        fs.collection(collection).get().get().documents

    fun readDataFromDocument(collection: String, document: String): QueryDocumentSnapshot? =
        readAllData(collection).findLast { doc: QueryDocumentSnapshot -> doc.id == document }
}

fun printData(documents: List<QueryDocumentSnapshot>) =
    documents.forEach { it.data.forEach { (k, v) -> println("$k: $v") } }