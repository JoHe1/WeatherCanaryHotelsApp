package org.ulpgc.dacd.control;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.ulpgc.dacd.model.Location;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    static Map<String, Location> mapIslandLocation = new HashMap<>();
        public static void main(String[] args) {
            WeatherProvider weatherProvider = new OpenWeatherMapProvider(args[0]);
            WeatherStore weatherStore = new JmsWeatherStore();
            WeatherController weatherController = new WeatherController(weatherProvider, weatherStore);
            loadStaticLocations(args[1]);
            periodicTask(weatherController);
        }
    private static void periodicTask(WeatherController weatherController) {
            /*
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
        scheduler.scheduleAtFixedRate(new Task(weatherController), initialDelay, 6 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        */
        new Task(weatherController).run();
        }


    private static void loadStaticLocations(String file) {
        try (Reader reader = new FileReader(file);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                mapIslandLocation.put(csvRecord.get(0), new Location(csvRecord.get(0), csvRecord.get(1),csvRecord.get(2), csvRecord.get(3)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
