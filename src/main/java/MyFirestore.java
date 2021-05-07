import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MyFirestore {

    private static MyFirestore myFirestoreInstance;
    private final Firestore firestoreDb;

    private MyFirestore(String projectId) throws IOException {
        firestoreDb = init(projectId);
    }

    public static MyFirestore getInstance(String projectId) throws IOException {
        if (Objects.isNull(myFirestoreInstance)) {
            myFirestoreInstance = new MyFirestore(projectId);
        }
        return myFirestoreInstance;
    }

    public Firestore getFirestoreDb() {
        return firestoreDb;
    }

    public void close() throws Exception {
        firestoreDb.close();
    }

    private static Firestore init(String projectId) throws IOException {
        InputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId(projectId)
                .build();
        FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore();
    }
}