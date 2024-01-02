package model;

public class Location {
    private final String island;
    private final String town;
    private final String latitude;
    private final String longitude;

    public Location(String island, String town, String latitude, String longitude) {
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

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
