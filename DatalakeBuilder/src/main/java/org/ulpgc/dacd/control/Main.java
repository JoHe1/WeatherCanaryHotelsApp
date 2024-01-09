package org.ulpgc.dacd.control;

public class Main {
    public static void main(String[] args) {
        Storable hotelStorable = new HotelStorable(args[0]);
        Storable WeatherStorable = new WeatherStorable(args[0]);
        StorableController controller = new StorableController(WeatherStorable, hotelStorable);
        controller.execute();
    }
}
