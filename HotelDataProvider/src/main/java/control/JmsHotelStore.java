package control;

import exceptions.JmsExceptionConection;
import model.Hotel;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class JmsHotelStore implements HotelStore {
    private final String topic = "information.hotel";
    private final String url = "tcp://localhost:61616";
    @Override
    public Connection connection() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(getUrl());
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
        } catch (JMSException e) {
            throw new JmsExceptionConection("Error establishing JMS connection", e);
        }
        return connection;
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
