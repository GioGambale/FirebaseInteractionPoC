import java.util.List;

public class City extends Entity {

    private String region;
    private int altitude;
    private List<String> municipalities;

    public City() {
    }

    public City(String name, String region, int people, int altitude, List<String> municipalities) {
        setName(name);
        this.region = region;
        setPeople(people);
        this.altitude = altitude;
        this.municipalities = municipalities;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public List<String> getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(List<String> municipalities) {
        this.municipalities = municipalities;
    }

    @Override
    public String toString() {
        return "CITY: { name = " + getName() + ", region = " + region + ", altitude = " + altitude +
                ", municipalities = " + municipalities.toString() + ", people: " + getPeople() + "}";
    }
}