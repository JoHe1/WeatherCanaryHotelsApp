package model;

import java.time.Instant;

public class Hotel {

    private final String name;
    private final String description;
    private final String link;
    private final String checkInDate;
    private final String checkInTime;
    private final String checkOutDate;
    private final String checkOutTime;
    private final Double price;
    private final Double rating;
    private final Integer numberOfReviews;
    private final Integer numberOfStars;

    private final Double latitude;
    private final Double longitude;

    private final Location location;
    private final Instant ts = Instant.now();
    private final String ss;

    public Hotel(String name, String description, String link, String checkInDate, String checkInTime, String checkOutDate, String checkOutTime, Double price, Double rating, Integer numberOfReviews, Integer numberOfStars, Double latitude, Double longitude, Location location, String ss) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
        this.price = price;
        this.rating = rating;
        this.numberOfReviews = numberOfReviews;
        this.numberOfStars = numberOfStars;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.ss = ss;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public Double getPrice() {
        return price;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getNumberOfReviews() {
        return numberOfReviews;
    }

    public Integer getNumberOfStars() {
        return numberOfStars;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Location getLocation() {
        return location;
    }

    public Instant getTs() {
        return ts;
    }

    public String getSs() {
        return ss;
    }
}
