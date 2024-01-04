package control;

import exceptions.SelectException;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataView {
    private final String datamartPath;

    public DataView(String datamartPath) {
        this.datamartPath = datamartPath;
    }

    public void viewHotelsByIslandCityAndDate(String island, String city, String entryDate) {
        try {
            Connection connection = open();
            Statement statement = connection.createStatement();
            String sqlSentence = "SELECT * FROM hotel " +
                    "WHERE island = '" + island + "' " +
                    "AND town = '" + city + "' AND " +
                    "checkInDate = '" + entryDate + "';";
            ResultSet resultSet = statement.executeQuery(sqlSentence);
            System.out.println("Hotels available in " + city + "," + island + " on " + entryDate + ":\n");
            while(resultSet.next()){
                System.out.println(
                        "Name of the Hotel: " + resultSet.getString("name") +
                                "\n Starts: " + resultSet.getString("numberOfStars") +"\n" +
                        "Description: " + resultSet.getString("description") + "\n" +
                                "Link: " + resultSet.getString("link") + "\n"
                );
                if (resultSet.getDouble("price") != 0.0) {
                    System.out.println("Price: " + resultSet.getString("price") + "\n");
                }
                else {
                    System.out.println("Price: Not available" + "\n");
                }
                System.out.println("Number of reviews: " + resultSet.getString("numberOfReviews") + "\n");
                if (resultSet.getDouble("rating") != 0.0) {
                    System.out.println("Rating: " + resultSet.getString("rating") + "\n");
                }
                else {
                    System.out.println("Rating: Not available" + "\n");
                }
                System.out.println("--------------------------------------------");
            }

        } catch (SQLException e) {
            throw new SelectException("Error viewing: " + e.getMessage());
        }
    }

    public void viewWeatherByHotelNameAndDate(String hotelName, String entryDate) {
        try {
            Connection connection = open();
            Statement statement = connection.createStatement();
            String sqlSentence = "SELECT island, town FROM hotel " +
                    "WHERE name = '" + hotelName + "' " +
                    "AND checkInDate = '" + entryDate + "';";
            ResultSet resultSet = statement.executeQuery(sqlSentence);
            String island = resultSet.getString("island");
            System.out.println(island);
            String town = resultSet.getString("town");
            System.out.println(town);
            String dateLimit = calculateDateLimit(entryDate);
            String sqlSentence2 = "SELECT * FROM prediction " +
                    "WHERE predictionTime BETWEEN '" + entryDate + "' AND '" + dateLimit + "' " +
                    "AND island = '" + island + "' " +
                    "AND town = '" + town + "';";
            ResultSet resultSet2 = statement.executeQuery(sqlSentence2);
            System.out.println("Weather prediction for " + town + "," + island + " from " + entryDate + " to " + dateLimit + ":\n");
            while(resultSet2.next()){
                System.out.println(
                        "Date: " + resultSet2.getString("predictionTime") + "\n" +
                        "Temperature: " + resultSet2.getString("temperature") + "\n" +
                        "Humidity: " + resultSet2.getString("humidity") + "\n" +
                        "Wind: " + resultSet2.getString("wind_velocity") + "\n" +
                        "Rain: " + resultSet2.getString("precipitation") + "\n" +
                        "Clouds: " + resultSet2.getString("cloud") + "\n" +
                        "--------------------------------------------"
                );
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
}
