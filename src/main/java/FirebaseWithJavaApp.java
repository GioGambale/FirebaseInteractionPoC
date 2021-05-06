import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FirebaseWithJavaApp {

    public static void main(String[] args) throws Exception {
        String projectId = "java-poc-99510";
        String collection = "Cities";
        String document = "AV";
        String document2 = "NA";

        System.out.println("####### INIT FIRESTORE #######");
        MyFirestore myFS = new MyFirestore(projectId);

        System.out.println("\n########## ADD ##########");
        System.out.println("Set document1.");
        myFS.setData(collection, document, new City("Avellino ", "Campania", 530, Lists.newArrayList("Atripalda", "Mercogliano", "Monteforte")), false);
        System.out.println("Set document2.");
        myFS.setData(collection, document2, new City("Napoli", "Campania", 940400, Lists.newArrayList("Pozzuoli", "Nola", "Pompei", "Sorrento")), false);
        System.out.println("Set document1 (merge case).");
        myFS.setData(collection, document, Collections.singletonMap("altitude", 348), true);

        System.out.println("\n########## UPDATE ##########");
        System.out.println("Update document1 (field 'people', new value 53000).");
        myFS.updateData(collection, document, Collections.singletonMap("people", 53000));

        System.out.println("\n########## READ ##########");
        System.out.println("Read and print all data:");
        List<QueryDocumentSnapshot> allData = myFS.readAllData(collection);
        myFS.printData(allData);

        System.out.println("\nRead and print only document2 ('NA') data:");
        Optional<QueryDocumentSnapshot> filteredDataOpt = myFS.readDataFromDocument(collection, document2);
        if (filteredDataOpt.isPresent()) {
            myFS.printData(Collections.singletonList(filteredDataOpt.get()));
        } else {
            System.out.println("No data.");
        }

        System.out.println("\n##### CLOSE FIRESTORE #####");
        myFS.close();
    }
}