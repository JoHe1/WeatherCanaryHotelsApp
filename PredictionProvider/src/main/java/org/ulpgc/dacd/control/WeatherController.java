package org.ulpgc.dacd.control;

import org.ulpgc.dacd.exceptions.ConnectionException;
import org.ulpgc.dacd.exceptions.URLInvalidException;
import org.ulpgc.dacd.model.Weather;
import java.util.List;

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
}
