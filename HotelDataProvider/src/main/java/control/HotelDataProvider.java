package control;

import model.Hotel;
import model.Location;

import java.util.List;

public interface HotelDataProvider {
    List<Hotel> get(Location location);
}
