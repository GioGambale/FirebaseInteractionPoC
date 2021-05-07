public class State extends Entity {

    private String capital;

    public State() {
    }

    public State(String name, String capital, int people) {
        setName(name);
        this.capital = capital;
        setPeople(people);
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "STATE: { name = " + getName() + ", capital = " + capital + ", people: " + getPeople() + "}";
    }
}