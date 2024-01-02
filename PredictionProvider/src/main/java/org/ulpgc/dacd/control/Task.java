package org.ulpgc.dacd.control;

import java.util.Date;

public class Task implements Runnable{
    WeatherController weatherController;

    public Task(WeatherController weatherController) {
        this.weatherController = weatherController;
    }

    @Override
    public void run() {
        weatherController.execute();
        System.out.println("The task execute at: " + new Date());
    }
}
