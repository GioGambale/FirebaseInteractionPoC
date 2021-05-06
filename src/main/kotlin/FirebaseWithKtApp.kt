fun main() {
    val projectId = "java-poc-99510"
    val collection = "Cities"
    val document = "BN"
    val document2 = "CE"

    println("####### INIT FIRESTORE #######")
    val myFS = MyFirestoreKt(projectId)

    println("\n########## ADD ##########")
    println("Set document1.")
    myFS.setData(
        collection,
        document,
        CityKt("Benevento ", "Campania", 577, listOf("Montesarchio", "Airola")),
        false
    )

    println("Set document2.")
    myFS.setData(
        collection,
        document2,
        CityKt("Caserta", "Campania", 73500, listOf("Marcianise", "Maddaloni", "Capua", "Casapulla")),
        false
    )
    println("Set document1 (merge case).")
    myFS.setData(
        collection,
        document,
        mapOf("altitude" to 135),
        true
    )

    println("\n########## UPDATE ##########")
    println("Update document1 (field 'people', new value 53000).")
    myFS.updateData(
        collection,
        document,
        mapOf("people" to 57700)
    )

    println("\n########## READ ##########")
    println("Read and print all data:")
    val allData = myFS.readAllData(collection)
    printData(allData)

    println("\nRead and print only document = 'CE':")
    val filteredData = myFS.readDataFromDocument(collection, document2)
    filteredData?.let { printData(listOf(it)) }

    println("\n##### CLOSE FIRESTORE #####")
    myFS.close()
}