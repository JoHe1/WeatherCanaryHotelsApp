package control;

import model.Hotel;

import javax.jms.Connection;

public class JmsHotelDataStore implements HotelDataStore{
    @Override
    public Connection connection() {
        return null;
    }

    @Override
    public void close(Connection connection) {

    }

    @Override
    public void save(Hotel hotel) {

    }
}
