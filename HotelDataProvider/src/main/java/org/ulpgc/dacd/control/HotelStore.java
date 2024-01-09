package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Hotel;

import javax.jms.Connection;

public interface HotelStore {
    Connection connection();
    void close(Connection connection);
    void save(Hotel hotel);
}
