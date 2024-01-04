package control;

public class BusinessUnitController {
    private final DataProcessor WeatherDataProcessor;
    private final DataProcessor HotelDataProcessor;
    private final MenuClient menuClient;

    public BusinessUnitController(DataProcessor weatherDataProcessor, DataProcessor hotelDataProcessor, MenuClient menuClient) {
        WeatherDataProcessor = weatherDataProcessor;
        HotelDataProcessor = hotelDataProcessor;
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
}
