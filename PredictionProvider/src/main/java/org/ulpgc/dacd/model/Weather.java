package org.ulpgc.dacd.model;
import java.io.Serializable;
import java.time.Instant;

public class Weather implements Serializable {
    private final double temperature;
    private final double precipitation;
    private final double humidity;
    private final double cloud;
    private final double wind_velocity;
    private final Instant predictionTime;
    private final Instant ts = Instant.now();
    private final Location location;
    private final String ss;

    public Weather(double temperature, double precipitation, double humidity, double cloud, double wind_velocity, Instant predictionTime, Location location, String ss) {
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.humidity = humidity;
        this.cloud = cloud;
        this.wind_velocity = wind_velocity;
        this.predictionTime = predictionTime;
        this.location = location;
        this.ss = ss;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getCloud() {
        return cloud;
    }

    public double getWind_velocity() {
        return wind_velocity;
    }

    public Instant getPredictionTime() {
        return predictionTime;
    }

    public Instant getTs() {
        return ts;
    }

    public Location getLocation() {
        return location;
    }

    public String getSs() {
        return ss;
    }
}
