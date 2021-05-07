import java.util.*

fun main() {
    val projectId = "java-poc-99510"
    val collection1 = "Cities"
    val collection2 = "Regions"
    val collection3 = "States"
    val document1 = "BAR"
    val document2 = "MAD"
    val document3 = "CAT"
    val document4 = "SPA"

    println("####### INIT #######")
    val myFs = MyFirestoreKt.getInstance(projectId)
    val myFsCollCities = MyFirestoreCollectionKt<CityKt>(myFs, collection1)
    val myFsCollRegions = MyFirestoreCollectionKt<RegionKt>(myFs, collection2)
    val myFsCollStates = MyFirestoreCollectionKt<StateKt>(myFs, collection3)
    val myFsDocBar = MyFirestoreDocumentKt(myFsCollCities, document1)
    val myFsDocMad = MyFirestoreDocumentKt(myFsCollCities, document2)
    val myFsDocCat = MyFirestoreDocumentKt(myFsCollRegions, document3)
    val myFsDocSpa = MyFirestoreDocumentKt(myFsCollStates, document4)

    println("\n######## ADD ########")
    println("Set document1.")
    myFsDocBar.setData(
        CityKt("Barcellona ", "Catalogna", 1637, 0, emptyList()),
        false
    )
    println("Set document2.")
    myFsDocMad.setData(
        CityKt("Madrid", "", 3223000, 0, emptyList()),
        false
    )
    println("Set document3.")
    myFsDocCat.setData(RegionKt("Catalogna", "spagna", 7566000), false)
    println("Set document4.")
    myFsDocSpa.setData(StateKt("Spagna", "Madrid", 49940000), false)

    println("\n########## MERGE ##########")
    println("Add 'altitude' value to document1.")
    myFsDocBar.setData(Collections.singletonMap<String, Any>("altitude", 9), true)

    println("\n########## UPDATE ##########")
    println("Update 'people' value of document1.")
    myFsDocBar.updateData(Collections.singletonMap<String, Any>("people", 1637000))

    println("\n########## READ ##########")
    println("Read and print all 'States' data:")
    myFsCollStates.readAllData(StateKt::class.java).forEach { println(it) }
    println("\nRead and print all 'Region' data:")
    myFsCollRegions.readAllData(RegionKt::class.java).forEach { println(it) }
    println("\nRead and print all 'Cities' data:")
    myFsCollCities.readAllData(CityKt::class.java).forEach { println(it) }

    println("\nRead and print only document2:")
    val city: CityKt? = myFsDocMad.readData(CityKt::class.java)
    city?.let { println((it)) }

    println("\n######## CLOSE ########")
    myFs.close()
}