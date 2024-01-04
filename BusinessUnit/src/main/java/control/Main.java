package control;

public class Main {
    public static void main(String[] args) { // TODO HACER QUE SE PASA EL PATH DONDE QUEREMOS LA BASE DE DATOS
        DataProcessor weatherDataProcessor = new WeatherDataProcessor();
        DataProcessor hotelDataProcessor = new HotelDataProcessor();
        DataView dataView = new DataView("datamart.db");
        MenuClient menuClient = new MenuClient(dataView);
        BusinessUnitController businessUnitController = new BusinessUnitController(weatherDataProcessor, hotelDataProcessor, menuClient);
        //businessUnitController.getWeatherDataProcessor().collectDataFromDatalake();
        //businessUnitController.getWeatherDataProcessor().collectDataFromBroker();
        //businessUnitController.getHotelDataProcessor().collectDataFromDatalake();
        //businessUnitController.getHotelDataProcessor().collectDataFromBroker();
        businessUnitController.getMenuClient().execute();

    }
}
