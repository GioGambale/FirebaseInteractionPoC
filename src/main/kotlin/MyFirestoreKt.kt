import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.CollectionReference
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.SetOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import java.io.FileInputStream
import java.io.InputStream

class MyFirestoreKt private constructor(projectId: String) {
    internal val firestoreDb: Firestore = init(projectId)

    companion object {
        @Volatile
        private var myFirestoreInstance: MyFirestoreKt? = null

        @Synchronized
        fun getInstance(projectId: String): MyFirestoreKt =
            myFirestoreInstance ?: MyFirestoreKt(projectId).also { myFirestoreInstance = it }
    }

    private fun init(projectId: String): Firestore {
        val serviceAccount: InputStream = FileInputStream("src/main/resources/serviceAccountKey.json")
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setProjectId(projectId)
            .build()
        FirebaseApp.initializeApp(options)
        return FirestoreClient.getFirestore()
    }

    fun close() {
        firestoreDb.close()
    }
}

class MyFirestoreCollectionKt<T : EntityKt>(myFirestore: MyFirestoreKt, collectionName: String) {
    internal val collection: CollectionReference = myFirestore.firestoreDb.collection(collectionName)

    fun readAllData(clazz: Class<T>): List<T> {
        val objs = mutableListOf<T>()
        val documentList = collection.get().get().documents
        for (document in documentList) {
            objs.add(document.toObject(clazz))
        }
        return objs
    }
}

class MyFirestoreDocumentKt<T : EntityKt>(collection: MyFirestoreCollectionKt<T>, documentName: String) {
    private val document: DocumentReference = collection.collection.document(documentName)

    fun setData(obj: T, merge: Boolean) {
        if (merge) {
            document.set(obj, SetOptions.merge()).get()
        } else {
            document.set(obj).get()
        }
    }

    fun setData(values: Map<String, Any>, merge: Boolean) {
        if (merge) {
            document.set(values, SetOptions.merge()).get()
        } else {
            document.set(values).get()
        }
    }

    fun updateData(updateValues: Map<String, Any>) {
        document.update(updateValues).get()
    }

    fun readData(clazz: Class<T>): T? {
        val documentSnapshot = document.get().get()
        return if (documentSnapshot.exists()) {
            documentSnapshot.toObject(clazz)
        } else {
            null
        }
    }
}