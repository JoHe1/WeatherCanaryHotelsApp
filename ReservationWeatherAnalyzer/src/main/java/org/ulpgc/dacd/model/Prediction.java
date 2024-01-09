package org.ulpgc.dacd.model;

public class Prediction {
    private String predictionTime;
    private String temperature;
    private String humidity;
    private String wind_velocity;
    private String precipitation;
    private String cloud;
    private String island;
    private String town;

    public Prediction(String predictionTime, String temperature, String humidity, String wind_velocity, String precipitation, String cloud, String island, String town) {
        this.predictionTime = predictionTime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind_velocity = wind_velocity;
        this.precipitation = precipitation;
        this.cloud = cloud;
        this.island = island;
        this.town = town;
    }

    public String getPredictionTime() {
        return predictionTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWind_velocity() {
        return wind_velocity;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getCloud() {
        return cloud;
    }

    public String getIsland() {
        return island;
    }

    public String getTown() {
        return town;
    }
}
