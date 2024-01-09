package org.ulpgc.dacd.control;

public interface DataProcessor {
    String collectDataFromDatalake();
    void saveDataToDatamart(String data);
    void collectDataFromBroker();
}
