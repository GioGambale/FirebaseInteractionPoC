import java.util.List;

public class City {

    private String name;
    private String region;
    private int people;
    private List<String> municipalities;

    public City() {
    }

    public City(String name, String region, int people, List<String> municipalities) {
        this.name = name;
        this.region = region;
        this.people = people;
        this.municipalities = municipalities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public List<String> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<String> municipalities) {
        this.municipalities = municipalities;
    }
}