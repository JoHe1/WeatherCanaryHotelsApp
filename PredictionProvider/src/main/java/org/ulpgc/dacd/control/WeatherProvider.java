package org.ulpgc.dacd.control;

import org.ulpgc.dacd.exceptions.ConnectionException;
import org.ulpgc.dacd.exceptions.URLInvalidException;
import org.ulpgc.dacd.model.Location;
import org.ulpgc.dacd.model.Weather;

import java.util.List;

public interface WeatherProvider {
    List<Weather> get(Location location) throws URLInvalidException, ConnectionException;
}
