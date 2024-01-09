package org.ulpgc.dacd.control;

import org.ulpgc.dacd.exceptions.SelectException;
import org.ulpgc.dacd.model.Hotel;
import org.ulpgc.dacd.model.Prediction;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataView {
    private final String datamartPath;

    public DataView(String datamartPath) {
        this.datamartPath = datamartPath;
    }

    public List<Hotel> viewHotelsByIslandCityAndDate(String island, String city, String entryDate, String dateLimit) {
        try {
            Connection connection = open();
            Statement statement = connection.createStatement();
            String sqlSentence = "SELECT DISTINCT name, description, link, numberOfStars, numberOfReviews, " +
                    "rating, price, checkInDate, checkInTime, checkOutDate, checkOutTime FROM hotel " +
                    "WHERE island = '" + island + "' " +
                    "AND town = '" + city + "' AND " +
                    "checkInDate >= '" + entryDate + "' AND " + "checkInDate < '" + dateLimit + "';";
            ResultSet resultSet = statement.executeQuery(sqlSentence);
            if (!resultSet.next()){
                return null;
            }else {
                List<Hotel> hotels = new ArrayList<>();
                while (resultSet.next()) {
                    Hotel hotel = new Hotel(
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("link"),
                            resultSet.getString("numberOfStars"),
                            resultSet.getString("numberOfReviews"),
                            resultSet.getString("rating"),
                            resultSet.getString("price"),
                            resultSet.getString("checkInDate"),
                            resultSet.getString("checkInTime"),
                            resultSet.getString("checkOutDate"),
                            resultSet.getString("checkOutTime")
                    );
                    hotels.add(hotel);
                }
                return hotels;
            }
        } catch (SQLException e) {
            throw new SelectException("Error viewing: " + e.getMessage());
        }
    }
    public List<Prediction> viewWeatherByHotelNameAndDate(String hotelName, String entryDate) {
        try {
            Connection connection = open();
            Statement statement = connection.createStatement();
            String sqlSentence = "SELECT island, town FROM hotel " +
                    "WHERE name = '" + hotelName + "' " +
                    "AND checkInDate = '" + entryDate + "';";
            ResultSet resultSet = statement.executeQuery(sqlSentence);
            if (!resultSet.next()){
                return null;
            }else {
                String island = resultSet.getString("island");
                String town = resultSet.getString("town");
                String dateLimit = calculateDateLimit(entryDate);
                String formattedEntryDateLessOne = LocalDate.parse(entryDate, DateTimeFormatter.ISO_LOCAL_DATE).minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
                String sqlSentence2 = "SELECT * FROM prediction " +
                        "WHERE predictionTime BETWEEN '" + formattedEntryDateLessOne + "' AND '" + dateLimit + "' " +
                        "AND island = '" + island + "' " +
                        "AND town = '" + town + "';";
                ResultSet resultSet2 = statement.executeQuery(sqlSentence2);
                if (!resultSet2.next()) {
                    return null;
                }else {
                    List<Prediction> predictions = new ArrayList<>();
                    while (resultSet2.next()) {
                        Prediction prediction = new Prediction(
                                resultSet2.getString("predictionTime"),
                                resultSet2.getString("temperature"),
                                resultSet2.getString("humidity"),
                                resultSet2.getString("wind_velocity"),
                                resultSet2.getString("precipitation"),
                                resultSet2.getString("cloud"),
                                resultSet2.getString("island"),
                                resultSet2.getString("town")
                        );
                        predictions.add(prediction);
                    }
                    return predictions;
                }
            }
        } catch (SQLException e) {
            throw new SelectException("Error viewing: " + e.getMessage());
        }
    }

    private String calculateDateLimit(String entryDate) {
        LocalDate dateLimit = LocalDate.parse(entryDate, DateTimeFormatter.ISO_LOCAL_DATE);
        dateLimit = dateLimit.plusDays(5);
        String dateLimitString = dateLimit.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return dateLimitString;
    }


    private Connection open() {
        String url = "jdbc:sqlite:" + this.datamartPath;
        Connection connection = null ;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.err.println("Error connection with databmart " + e.getMessage());
        }
        return connection;
    }
    public String bold() {
        return "\u001B[1m";
    }
    public String reset() {
        return "\u001B[0m";
    }
    public String red() {
        return "\u001B[31m";
    }
    public String green() {
        return "\u001B[32m";
    }
    public String blue() {
        return "\u001B[34m";
    }
}
