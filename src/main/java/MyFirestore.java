import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class MyFirestore {

    private final Firestore fs;

    public MyFirestore(String projectId) throws IOException {
        fs = init(projectId);
    }

    public void setData(String collection, String document, City city, boolean merge) throws ExecutionException, InterruptedException {
        if (merge) {
            fs.collection(collection).document(document).set(city, SetOptions.merge()).get();
        } else {
            fs.collection(collection).document(document).set(city).get();
        }
    }

    public void setData(String collection, String document, Map<String, Object> values, boolean merge) throws ExecutionException, InterruptedException {
        if (merge) {
            fs.collection(collection).document(document).set(values, SetOptions.merge()).get();
        } else {
            fs.collection(collection).document(document).set(values).get();
        }
    }

    public void updateData(String collection, String document, Map<String, Object> updateValues) throws ExecutionException, InterruptedException {
        fs.collection(collection).document(document).update(updateValues).get();
    }

    public List<QueryDocumentSnapshot> readAllData(String collection) throws ExecutionException, InterruptedException {
        return fs.collection(collection).get().get().getDocuments();
    }

    public Optional<QueryDocumentSnapshot> readDataFromDocument(String collection, String document) throws ExecutionException, InterruptedException {
        return readAllData(collection).stream().filter(doc -> doc.getId().equals(document)).findFirst();
    }

    public void printData(List<QueryDocumentSnapshot> documents) {
        documents.forEach(
                doc -> doc.getData().forEach(
                        (key, value) -> System.out.println(key + ": " + value)
                )
        );
    }

    public void close() throws Exception {
        fs.close();
    }

    private Firestore init(String projectId) throws IOException {
        InputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId(projectId)
                .build();
        FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore();
    }
}