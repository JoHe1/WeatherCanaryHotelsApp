package org.ulpgc.dacd.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.dacd.exceptions.JmsExceptionConection;
import org.ulpgc.dacd.model.Weather;

import javax.jms.*;
import java.time.Instant;

public class JmsWeatherStore implements WeatherStore{
    public final String url = "tcp://localhost:61616";
    public final String subject = "prediction.Weather";


    @Override
    public Connection connection() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
        } catch (JMSException e) {
            throw new JmsExceptionConection("Error establishing JMS connection",e);
        }
        return connection;
    }

    @Override
    public void close(Connection connection) {
        try {
            connection.close();
        } catch (JMSException e) {
            throw new JmsExceptionConection("Error closing JMS connection",e);
        }
    }

    @Override
    public void save(Weather weather) {
        try{
        Connection connection = connection();

        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        Topic destination = session.createTopic(subject);

        MessageProducer producer = session.createProducer(destination);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
        String weatherJson = gson.toJson(weather);
        TextMessage message = session
                .createTextMessage(weatherJson);

        producer.send(message);
        close(connection);
        }catch (JMSException e) {
            throw new JmsExceptionConection("Error sending message to JMS",e);
        }
    }
}
