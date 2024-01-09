package control;

import view.MenuClient;

public class BusinessUnitController {
    private final DataProcessor WeatherDataProcessor;
    private final DataProcessor HotelDataProcessor;
    private final MenuClient menuClient;

    public BusinessUnitController(DataProcessor weatherDataProcessor, DataProcessor hotelDataProcessor, MenuClient menuClient) {
        this.WeatherDataProcessor = weatherDataProcessor;
        this.HotelDataProcessor = hotelDataProcessor;
        this.menuClient = menuClient;
    }

    public DataProcessor getWeatherDataProcessor() {
        return WeatherDataProcessor;
    }

    public DataProcessor getHotelDataProcessor() {
        return HotelDataProcessor;
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
