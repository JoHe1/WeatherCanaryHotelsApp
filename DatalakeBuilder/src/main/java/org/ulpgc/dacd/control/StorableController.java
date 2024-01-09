package org.ulpgc.dacd.control;

public class StorableController {
    private final Storable weatherStorable;
    private final Storable hotelStorable;

    public StorableController(Storable weatherStorable, Storable hotelStorable) {
        this.weatherStorable = weatherStorable;
        this.hotelStorable = hotelStorable;
    }

    public Storable getWeatherStorable() {
        return weatherStorable;
    }

    public Storable getHotelStorable() {
        return hotelStorable;
    }

    public void execute() {
        getWeatherStorable().save();
        getHotelStorable().save();
    }
}
