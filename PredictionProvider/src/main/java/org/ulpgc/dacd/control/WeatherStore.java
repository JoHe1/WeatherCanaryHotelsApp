package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Weather;

import javax.jms.Connection;

public interface WeatherStore {
    Connection connection();
    void close(Connection connection);
    void save(Weather weather);
}
