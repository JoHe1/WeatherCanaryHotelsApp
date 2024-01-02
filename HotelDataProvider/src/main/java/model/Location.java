package model;

public class Location {
    private final String island;
    private final String town;
    private final Double latitude;
    private final Double longitude;

    public Location(String island, String town, Double latitude, Double longitude) {
        this.island = island;
        this.town = town;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIsland() {
        return island;
    }

    public String getTown() {
        return town;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
