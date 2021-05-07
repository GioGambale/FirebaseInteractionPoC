import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.SetOptions;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MyFirestoreDocument<T extends Entity> {

    private final DocumentReference document;

    public MyFirestoreDocument(MyFirestoreCollection<T> collection, String document) {
        this.document = collection.getCollection().document(document);
    }

    public void setData(T obj, boolean merge) throws ExecutionException, InterruptedException {
        if (merge) {
            document.set(obj, SetOptions.merge()).get();
        } else {
            document.set(obj).get();
        }
    }

    public void setData(Map<String, Object> values, boolean merge) throws ExecutionException, InterruptedException {
        if (merge) {
            document.set(values, SetOptions.merge()).get();
        } else {
            document.set(values).get();
        }
    }

    public void updateData(Map<String, Object> updateValues) throws ExecutionException, InterruptedException {
        document.update(updateValues).get();
    }

    public T readData(Class<T> clazz) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = document.get().get();
        if (documentSnapshot.exists()) {
            return documentSnapshot.toObject(clazz);
        } else {
            return null;
        }
    }
}