package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Hotel;
import org.ulpgc.dacd.model.Location;

import java.util.List;

public interface HotelProvider {
    List<Hotel> get(Location location);
}
