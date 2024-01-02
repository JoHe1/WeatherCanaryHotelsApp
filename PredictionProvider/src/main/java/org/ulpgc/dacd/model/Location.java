package org.ulpgc.dacd.model;

public class Location {
    private final String island;
    private final String latitude;
    private final String longitude;
    private final String town;

    public Location(String island, String latitude, String longitude, String town) {
        this.island = island;
        this.latitude = latitude;
        this.longitude = longitude;
        this.town = town;
    }

    public String getIsland() {
        return island;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTown() {
        return town;
    }

}
