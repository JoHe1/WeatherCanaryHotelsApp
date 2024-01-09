package org.ulpgc.dacd.control;

import org.ulpgc.dacd.view.MenuClient;

public class BusinessUnitController {
    private final DataProcessor weatherDataProcessor;
    private final DataProcessor hotelDataProcessor;
    private final MenuClient menuClient;

    public BusinessUnitController(DataProcessor weatherDataProcessor, DataProcessor hotelDataProcessor, MenuClient menuClient) {
        this.weatherDataProcessor = weatherDataProcessor;
        this.hotelDataProcessor = hotelDataProcessor;
        this.menuClient = menuClient;
    }

    public DataProcessor getWeatherDataProcessor() {
        return weatherDataProcessor;
    }

    public DataProcessor getHotelDataProcessor() {
        return hotelDataProcessor;
    }

    public MenuClient getMenuClient() {
        return menuClient;
    }
    public void execute() {
        this.getWeatherDataProcessor().collectDataFromDatalake();
        this.getWeatherDataProcessor().collectDataFromBroker();
        this.getHotelDataProcessor().collectDataFromDatalake();
        this.getHotelDataProcessor().collectDataFromBroker();
        this.getMenuClient().execute();
    }
}
