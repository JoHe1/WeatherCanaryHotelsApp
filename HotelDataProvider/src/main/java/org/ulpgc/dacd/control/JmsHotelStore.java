package org.ulpgc.dacd.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ulpgc.dacd.exceptions.JmsExceptionConection;
import org.ulpgc.dacd.model.Hotel;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.time.Instant;

public class JmsHotelStore implements HotelStore {
    private final String topic = "information.Hotel";
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
        try {
            connection.close();
        } catch (JMSException e) {
            throw new JmsExceptionConection("Error closing JMS connection", e);
        }
    }

    @Override
    public void save(Hotel hotel) {
        try {
            Connection connection = connection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic(getTopic());
            MessageProducer producer = session.createProducer(destination);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                    .create();
            String hotelJson = gson.toJson(hotel);
            TextMessage message = session.createTextMessage(hotelJson);
            producer.send(message);
            close(connection);
        } catch (JMSException e) {
            throw new JmsExceptionConection("Error sending message to JMS", e);
        }
    }

    public String getTopic() {
        return topic;
    }

    public String getUrl() {
        return url;
    }
}
