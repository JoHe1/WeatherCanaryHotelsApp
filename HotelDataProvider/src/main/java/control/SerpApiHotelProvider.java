package control;

import model.Hotel;
import model.Location;

import java.util.List;

public class SerpApiHotelProvider implements HotelDataProvider{
    private final String ss = "SerpApi";
    private final String apiKey;

    public SerpApiHotelProvider(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<Hotel> get(Location location) {
        return null;
    }
}
