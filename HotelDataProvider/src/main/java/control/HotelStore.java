package control;

import model.Hotel;

import javax.jms.Connection;

public interface HotelStore {
    Connection connection();
    void close(Connection connection);
    void save(Hotel hotel);
}
