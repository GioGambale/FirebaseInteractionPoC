import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Objects;

public class FirebaseWithJavaApp {

    public static void main(String[] args) throws Exception {
        String projectId = "java-poc-99510";
        String collection1 = "Cities";
        String collection2 = "Regions";
        String collection3 = "States";
        String document1 = "AV";
        String document2 = "NA";
        String document3 = "CAM";
        String document4 = "IT";

        System.out.println("######### INIT #########");
        MyFirestore myFs = MyFirestore.getInstance(projectId);
        MyFirestoreCollection<City> myFsCollCities = new MyFirestoreCollection<>(myFs, collection1);
        MyFirestoreCollection<Region> myFsCollRegions = new MyFirestoreCollection<>(myFs, collection2);
        MyFirestoreCollection<State> myFsCollStates = new MyFirestoreCollection<>(myFs, collection3);
        MyFirestoreDocument<City> myFsDocAv = new MyFirestoreDocument<>(myFsCollCities, document1);
        MyFirestoreDocument<City> myFsDocNa = new MyFirestoreDocument<>(myFsCollCities, document2);
        MyFirestoreDocument<Region> myFsDocCam = new MyFirestoreDocument<>(myFsCollRegions, document3);
        MyFirestoreDocument<State> myFsDocIt = new MyFirestoreDocument<>(myFsCollStates, document4);

        System.out.println("\n########## ADD ##########");
        System.out.println("Set document1.");
        myFsDocAv.setData(new City("Avellino ", "Campania", 530, 0, Lists.newArrayList("Atripalda", "Mercogliano", "Monteforte")), false);
        System.out.println("Set document2.");
        myFsDocNa.setData(new City("Napoli", "Campania", 940400, 0, Lists.newArrayList("Pozzuoli", "Nola", "Pompei", "Sorrento")), false);
        System.out.println("Set document3.");
        myFsDocCam.setData(new Region("Campania", "Italia", 5800000), false);
        System.out.println("Set document4.");
        myFsDocIt.setData(new State("Italia", "Roma", 60000000), false);

        System.out.println("\n########## MERGE ##########");
        System.out.println("Add 'altitude' value to document1.");
        myFsDocAv.setData(Collections.singletonMap("altitude", 348), true);

        System.out.println("\n########## UPDATE ##########");
        System.out.println("Update 'people' value of document1.");
        myFsDocAv.updateData(Collections.singletonMap("people", 53000));

        System.out.println("\n########## READ ##########");
        System.out.println("Read and print all 'States' data:");
        myFsCollStates.readAllData(State.class).forEach(System.out::println);
        System.out.println("\nRead and print all 'Region' data:");
        myFsCollRegions.readAllData(Region.class).forEach(System.out::println);
        System.out.println("\nRead and print all 'Cities' data:");
        myFsCollCities.readAllData(City.class).forEach(System.out::println);

        System.out.println("\nRead and print only document2:");
        City city = myFsDocNa.readData(City.class);
        if (Objects.isNull(city)) {
            System.out.println("ERROR: No such document!");
        } else {
            System.out.println(city);
        }

        System.out.println("\n### WAITING FOR CHANGES (on doc1) FOR 30 SECONDS  ###");
        ListenerRegistration listener = listenData(myFs.getFirestoreDb(), collection1, document1);
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < 30000) {
            // nop
        }

        System.out.println("\n######### CLOSE #########");
        listener.remove();
        myFs.close();
    }

    private static ListenerRegistration listenData(Firestore db, String collection, String document) {
        DocumentReference docRef = db.collection(collection).document(document);
        return docRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                System.err.println("Listen failed: " + e);
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                System.out.println("Current data: " + snapshot.toObject(City.class));
            } else {
                System.out.print("Current data: null");
            }
        });
    }
}