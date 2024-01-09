package model;

public class Hotel {
    private String name;
    private String description;
    private String link;
    private String numberOfStars;
    private String numberOfReviews;
    private String rating;
    private String price;
    private String checkInDate;
    private String checkInTime;
    private String checkOutDate;
    private String checkOutTime;

    public Hotel(String name, String description, String link, String numberOfStars, String numberOfReviews, String rating, String price, String checkInDate, String checkInTime, String checkOutDate, String checkOutTime) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.numberOfStars = numberOfStars;
        this.numberOfReviews = numberOfReviews;
        this.rating = rating;
        this.price = price;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
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

    public String getNumberOfStars() {
        return numberOfStars;
    }

    public String getNumberOfReviews() {
        return numberOfReviews;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
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
}
