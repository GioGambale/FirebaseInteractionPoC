public class Region extends Entity {

    private String state;

    public Region() {
    }

    public Region(String name, String state, int people) {
        setName(name);
        this.state = state;
        setPeople(people);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "REGION: { name = " + getName() + ", state = " + state + ", people: " + getPeople() + "}";
    }
}