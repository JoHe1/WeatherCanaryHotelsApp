package control;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import exceptions.ConnectionException;
import exceptions.URLInvalidException;
import model.Hotel;
import model.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerpApiHotelProvider implements HotelProvider {
    private final String ss = "SerpApi";
    private final String apiKey;

    public SerpApiHotelProvider(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<Hotel> get(Location location) {
        List<Hotel> hotels = new ArrayList<>();
        HttpURLConnection connection = HttpURLConnection(location);
        int responseCode = 0;
        try {
            responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Map<String, String> dates = dateTimeCheckInCheckOut();
            for (JsonElement jsonElement : JsonParser.parseString(response.toString()).getAsJsonObject().getAsJsonArray("properties")) {
                String name = jsonElement.getAsJsonObject().get("name").getAsString();
                String type = jsonElement.getAsJsonObject().get("type").getAsString();
                String description = "No description";
                if(jsonElement.getAsJsonObject().has("description")){
                    description = jsonElement.getAsJsonObject().get("description").getAsString();
                }
                String link = "No link";
                if(jsonElement.getAsJsonObject().has("link")){
                    link = jsonElement.getAsJsonObject().get("link").getAsString();
                }
                String checkInDate = dates.get("checkInDateFormatted");
                String checkInTime = "No check in time";
                if (jsonElement.getAsJsonObject().has("check_in_time")) {
                    checkInTime = jsonElement.getAsJsonObject().get("check_in_time").getAsString();
                }
                String checkOutDate = dates.get("checkOutDateFormatted");
                String checkOutTime = "No check out time";
                if (jsonElement.getAsJsonObject().has("check_out_time")) {
                    checkOutTime = jsonElement.getAsJsonObject().get("check_out_time").getAsString();
                }
                Double price = 0.0;
                if (jsonElement.getAsJsonObject().has("price")) {
                    price = jsonElement.getAsJsonObject().get("rate_per_night").getAsJsonObject().get("extracted_lowest").getAsDouble();
                }
                Double rating = null;
                if (jsonElement.getAsJsonObject().has("overall_rating")) {
                    rating = jsonElement.getAsJsonObject().get("overall_rating").getAsDouble();
                }
                Integer numberOfReviews = null;
                if (jsonElement.getAsJsonObject().has("reviews")) {
                    numberOfReviews = jsonElement.getAsJsonObject().get("reviews").getAsInt();
                }
                Integer numberOfStars = null;
                if (jsonElement.getAsJsonObject().has("extracted_hotel_class")) {
                    numberOfStars = jsonElement.getAsJsonObject().get("extracted_hotel_class").getAsInt();
                }
                Double latitude = jsonElement.getAsJsonObject().get("gps_coordinates").getAsJsonObject().get("latitude").getAsDouble();
                Double longitude = jsonElement.getAsJsonObject().get("gps_coordinates").getAsJsonObject().get("longitude").getAsDouble();
                hotels.add(new Hotel(name, type, description, link, checkInDate, checkInTime, checkOutDate, checkOutTime, price, rating, numberOfReviews, numberOfStars, latitude, longitude, location, getSs()));
            }
        } catch (IOException e) {
            throw new ConnectionException("Connection error" + e.getMessage());
        }
        return hotels;
    }

    private HttpURLConnection HttpURLConnection(Location location) {
        HttpURLConnection connection = null;
        Map<String, String> dates = dateTimeCheckInCheckOut();
        try {
            String url = "https://serpapi.com/search.json?engine=google_hotels" +
                    "&q=Hoteles+en+" + location.getTown() + "+,+" + location.getIsland() +
                    "&gl=es" +
                    "&hl=es" +
                    "&currency=EUR" +
                    "&check_in_date=" + dates.get("checkInDateFormatted") +
                    "&check_out_date=" + dates.get("checkOutDateFormatted") +
                    "&api_key=" + getApiKey();
            URL urlConnection = new URL(url);
            connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new ConnectionException("URL is invalid" + e.getMessage());
        } catch (IOException e) {
            throw new URLInvalidException("Connection error" + e.getMessage());
        }
        return connection;
    }
    private Map<String, String> dateTimeCheckInCheckOut(){
        Map<String, String> dates = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkInDate = LocalDate.now().plusDays(1);
        String checkInDateFormatted = checkInDate.format(formatter);
        LocalDate checkOutDate = LocalDate.now().plusDays(4);
        String checkOutDateFormatted = checkOutDate.format(formatter);
        dates.put("checkInDateFormatted", checkInDateFormatted);
        dates.put("checkOutDateFormatted", checkOutDateFormatted);
        return dates;
    }

    public String getSs() {
        return ss;
    }

    public String getApiKey() {
        return apiKey;
    }
}
