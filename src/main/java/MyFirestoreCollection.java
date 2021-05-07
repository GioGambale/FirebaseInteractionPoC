import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyFirestoreCollection<T extends Entity> {
    private final CollectionReference collection;

    public MyFirestoreCollection(MyFirestore myFirestore, String collection) {
        this.collection = myFirestore.getFirestoreDb().collection(collection);
    }

    public List<T> readAllData(Class<T> clazz) throws ExecutionException, InterruptedException {
        List<T> objs = new ArrayList<>();
        List<QueryDocumentSnapshot> documentList = collection.get().get().getDocuments();
        for (QueryDocumentSnapshot document : documentList) {
            objs.add(document.toObject(clazz));
        }
        return objs;
    }

    public CollectionReference getCollection() {
        return collection;
    }
}