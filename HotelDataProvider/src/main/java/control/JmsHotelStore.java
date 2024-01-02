package control;

import model.Hotel;

import javax.jms.Connection;

public class JmsHotelStore implements HotelStore {
    private final String topic = "information.hotel";
    private final String url = "tcp://localhost:61616";
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

    public String getTopic() {
        return topic;
    }

    public String getUrl() {
        return url;
    }
}
