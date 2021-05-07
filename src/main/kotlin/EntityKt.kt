open class EntityKt(val name: String, val people: Int) {
    constructor() : this("", 0)
}

class CityKt(
    name: String,
    var region: String,
    people: Int,
    var altitude: Int,
    var municipalities: List<String>
) :
    EntityKt(name, people) {
    constructor() : this("", "", 0, 0, emptyList())

    override fun toString(): String =
        "CITY: { name = $name, region = $region, altitude = $altitude, municipalities = $municipalities, people: $people }"
}

class RegionKt(name: String, var state: String, people: Int) : EntityKt(name, people) {
    constructor() : this("", "", 0)

    override fun toString(): String =
        "REGION: { name = $name, state = $state, people: $people }"
}

class StateKt(name: String, var capital: String, people: Int) : EntityKt(name, people) {
    constructor() : this("", "", 0)

    override fun toString(): String =
        "STATE: { name = $name, capital = $capital, people: $people }"
}