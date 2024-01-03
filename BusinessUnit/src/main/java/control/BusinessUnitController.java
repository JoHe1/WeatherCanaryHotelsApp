package control;

public class BusinessUnitController {
    private final DataProcessor WeatherDataProcessor;
    private final DataProcessor HotelDataProcessor;

    public BusinessUnitController(DataProcessor weatherDataProcessor, DataProcessor hotelDataProcessor) {
        WeatherDataProcessor = weatherDataProcessor;
        HotelDataProcessor = hotelDataProcessor;
    }

    public DataProcessor getWeatherDataProcessor() {
        return WeatherDataProcessor;
    }

    public DataProcessor getHotelDataProcessor() {
        return HotelDataProcessor;
    }
}
