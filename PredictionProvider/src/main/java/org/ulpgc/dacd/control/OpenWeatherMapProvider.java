package org.ulpgc.dacd.control;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.ulpgc.dacd.exceptions.ConnectionException;
import org.ulpgc.dacd.exceptions.URLInvalidException;
import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherMapProvider implements WeatherProvider{
    public final String apikey;
    public final String ss = "OpenWeatherMap";
    public OpenWeatherMapProvider(String apikey) {
        this.apikey = apikey;
    }


    @Override
    public List<Weather> get(Location location) throws URLInvalidException, ConnectionException {
        List<Weather> weathers = new ArrayList<>();
        HttpURLConnection connection = getHttpURLConnection(location);
        int responseCode = 0;
        try {
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                for (JsonElement jsonElement : JsonParser.parseString(response.toString()).getAsJsonObject().getAsJsonArray("list")) {
                    String dt_txt = jsonElement.getAsJsonObject().get("dt_txt").getAsString();
                    String hour = dt_txt.substring(11,13);
                    //if (hour.equals("12")){ TODO QUITAR COMETNARIOS
                        double temp = jsonElement.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
                        double pop = jsonElement.getAsJsonObject().get("pop").getAsDouble();
                        double humidity = jsonElement.getAsJsonObject().get("main").getAsJsonObject().get("humidity").getAsDouble();
                        double clouds = jsonElement.getAsJsonObject().get("clouds").getAsJsonObject().get("all").getAsDouble();
                        double wind = jsonElement.getAsJsonObject().get("wind").getAsJsonObject().get("speed").getAsDouble();
                        Instant instantPrediction = Instant.ofEpochSecond(jsonElement.getAsJsonObject().get("dt").getAsLong());
                        weathers.add(new Weather(temp, pop, humidity, clouds, wind, instantPrediction, location, ss));
                    //}
                }
            }
        } catch (IOException e) {
            throw new ConnectionException("Connection error" + e.getMessage());
        }

        return weathers;
    }

    private HttpURLConnection getHttpURLConnection(Location location) throws URLInvalidException, ConnectionException {
        HttpURLConnection connection = null;
        try {
            String url = "https://api.openweathermap.org/data/2.5/forecast?"
                    + "lat=" + location.getLatitude()
                    + "&lon=" + location.getLongitude()
                    + "&cnt=2" // TODO QUITARLO
                    + "&appid=" + apikey
                    + "&units=metric";
            URL obj = new URL(url);
            connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
        } catch (MalformedURLException e) {
            throw new URLInvalidException("URL is invalid" + e.getMessage());
        } catch (IOException e) {
            throw new ConnectionException("Connection error" + e.getMessage());
        }

        return connection;
    }
}
