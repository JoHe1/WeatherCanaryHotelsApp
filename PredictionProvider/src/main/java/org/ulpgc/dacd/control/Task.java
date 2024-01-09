package org.ulpgc.dacd.control;

import java.util.Date;

public class Task implements Runnable{
    private final WeatherController weatherController;

    public Task(WeatherController weatherController) {
        this.weatherController = weatherController;
    }

    @Override
    public void run() {
        System.out.println("The task execute at: " + new Date());
        getWeatherController().execute();
    }

    public WeatherController getWeatherController() {
        return weatherController;
    }
}
