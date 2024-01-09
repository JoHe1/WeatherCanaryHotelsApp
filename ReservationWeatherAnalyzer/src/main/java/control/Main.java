package control;

import view.MenuClient;

public class Main {
    public static void main(String[] args) {
        DataProcessor weatherDataProcessor = new WeatherDataProcessor(args[0], args[1]);
        DataProcessor hotelDataProcessor = new HotelDataProcessor(args[0], args[1]);
        DataView dataView = new DataView(args[0] + "datamart.db");
        MenuClient menuClient = new MenuClient(dataView);
        BusinessUnitController businessUnitController = new BusinessUnitController(weatherDataProcessor, hotelDataProcessor, menuClient);
        businessUnitController.execute();
    }
}
