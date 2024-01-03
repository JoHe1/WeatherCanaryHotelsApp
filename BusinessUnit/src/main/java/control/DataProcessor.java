package control;

public interface DataProcessor {
    String collectDataFromDatalake();
    void saveDataToDatamart(String data);
    String collectDataFromBroker();
}
