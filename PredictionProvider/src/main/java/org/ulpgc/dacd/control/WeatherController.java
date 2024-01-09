package org.ulpgc.dacd.control;

import org.ulpgc.dacd.exceptions.ConnectionException;
import org.ulpgc.dacd.exceptions.URLInvalidException;
import org.ulpgc.dacd.model.Weather;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.ulpgc.dacd.control.Main.mapIslandLocation;

public class WeatherController {
    public final WeatherProvider weatherProvider;
    public final WeatherStore weatherStore;

    public WeatherController(WeatherProvider weatherProvider, WeatherStore weatherStore) {
        this.weatherProvider = weatherProvider;
        this.weatherStore = weatherStore;
    }

    public void execute() {
        mapIslandLocation.forEach((island,location) -> {
            try {
                List<Weather> weathers = weatherProvider.get(location);
                for (Weather weather : weathers) {
                    weatherStore.save(weather);
                }
            } catch (URLInvalidException | ConnectionException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void periodicTask() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Calendar now = Calendar.getInstance();
        Calendar nextRun = Calendar.getInstance();
        nextRun.set(Calendar.HOUR_OF_DAY, 12);
        nextRun.set(Calendar.MINUTE, 0);
        nextRun.set(Calendar.SECOND, 0);
        nextRun.set(Calendar.MILLISECOND, 0);
        if (now.after(nextRun)) {
            nextRun.add(Calendar.DAY_OF_YEAR, 1);
        }
        long initialDelay = nextRun.getTimeInMillis() - System.currentTimeMillis();
        scheduler.scheduleAtFixedRate(new Task(this), initialDelay, 6 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
    }
}
