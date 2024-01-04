package control;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import exceptions.BrokerConnectionException;
import exceptions.DatabaseSqliteException;
import exceptions.MessageBrokerException;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataProcessor implements DataProcessor{
    private final String datamartUrl = "jdbc:sqlite:datamart.db"; // TODO PASAR POR ARGUMENTO
    private final String topicName = "prediction.Weather";
    @Override
    public String collectDataFromDatalake() {
        Path path = getPathFile(); // TODO CONTROLADOR DE ERRORES ¿?
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                saveDataToDatamart(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Path getPathFile() {
        String directory = "datalake/eventstore/prediction.Weather/OpenWeatherMap";
        Path result = null;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        for (int i = 0; i <= 15; i++) {
            String fileName = date.format(formatter) + ".events";
            Path path = Paths.get(directory, fileName);
            if (path.toFile().exists()) {
                result = path;
                break;
            }
            date = date.minusDays(1);
        }
        return result;
    }

    @Override
    public void saveDataToDatamart(String data) {
        createWeatherTable();
        List<Object> listWeatherData = collectInformation(data);
        insertWeatherData(listWeatherData);
    }

    private void insertWeatherData(List<Object> listWeatherData) {
        try(Connection connection = DriverManager.getConnection(getDatamartUrl())) {
            Statement statement = connection.createStatement();
            statement.execute("INSERT OR REPLACE INTO prediction VALUES (" +
                    "'" + listWeatherData.get(0) + "'," +
                    "'" + listWeatherData.get(1) + "'," +
                    "'" + listWeatherData.get(2) + "'," +
                    "" + listWeatherData.get(3) + "," +
                    "" + listWeatherData.get(4) + "," +
                    "" + listWeatherData.get(5) + "," +
                    "" + listWeatherData.get(6) + "," +
                    "" + listWeatherData.get(7) + "" +
                    ");"
            );
        } catch (SQLException e) {
            throw new DatabaseSqliteException(e.getMessage());
        }
    }

    private List<Object> collectInformation(String data) {
        ArrayList<Object> listWeatherData = new ArrayList<>(); //TODO HACER UN MAP PARA QUE QUEDE MAS MONO ¿?
        JsonElement jsonElement = JsonParser.parseString(data);
        listWeatherData.add(formatedPredictionTime(jsonElement.getAsJsonObject().get("predictionTime").getAsString()));
        listWeatherData.add(formatedIsland(jsonElement.getAsJsonObject().get("location").getAsJsonObject().get("island").getAsString()));
        listWeatherData.add(formatedTown(jsonElement.getAsJsonObject().get("location").getAsJsonObject().get("town").getAsString()));
        listWeatherData.add(jsonElement.getAsJsonObject().get("temperature").getAsDouble());
        listWeatherData.add(jsonElement.getAsJsonObject().get("precipitation").getAsDouble());
        listWeatherData.add(jsonElement.getAsJsonObject().get("humidity").getAsDouble());
        listWeatherData.add(jsonElement.getAsJsonObject().get("cloud").getAsDouble());
        listWeatherData.add(jsonElement.getAsJsonObject().get("wind_velocity").getAsDouble());
        return listWeatherData;
    }

    private String formatedIsland(String island) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < island.length(); i++) {
            char character = island.charAt(i);
            if (Character.isUpperCase(character) && i > 0) {
                result.append(' ');
            }
            result.append(character);
        }
        return result.toString();
    }
    private String formatedTown(String town) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < town.length(); i++) {
            char character = town.charAt(i);
            if (Character.isUpperCase(character) && i > 0) {
                result.append(' ');
            }
            result.append(character);
        }
        return result.toString();
    }

    private String formatedPredictionTime(String predictionTime) {
        Instant instant = Instant.parse(predictionTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateString = formatter.format(instant.atZone(java.time.ZoneId.systemDefault()));
        return formattedDateString;
    }

    private void createWeatherTable() {
        try(Connection connection = DriverManager.getConnection(getDatamartUrl())) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS prediction (" +
                    "predictionTime TEXT," +
                    "island TEXT," +
                    "town TEXT," +
                    "temperature REAL," +
                    "precipitation REAL," +
                    "humidity REAL," +
                    "cloud REAL," +
                    "wind_velocity REAL," +
                    "PRIMARY KEY (predictionTime, island, town)" +
                    ");"
            );
        } catch (SQLException e) {
            throw new DatabaseSqliteException(e.getMessage());
        }
    }

    @Override
    public void collectDataFromBroker() {
        createWeatherTable();
        MessageConsumer subscriber = initialiseSuscriber();
        try {
            subscriber.setMessageListener(message -> {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    List<Object> listWeatherData = collectInformation(text);
                    insertWeatherData(listWeatherData);
                } catch (JMSException e) {
                    throw new MessageBrokerException(e.getMessage());
                }

            });
        } catch (JMSException e) {
            throw new BrokerConnectionException(e.getMessage());
        }
    }

    private MessageConsumer initialiseSuscriber() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            javax.jms.Connection connection = factory.createConnection();
            connection.setClientID("BusinessUnit" + getTopicName());
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(getTopicName());
            MessageConsumer subscriber = session.createConsumer(topic);
            return subscriber;
        } catch (JMSException e) {
            throw new BrokerConnectionException(e.getMessage());
        }
    }

    public String getDatamartUrl() {
        return datamartUrl;
    }

    public String getTopicName() {
        return topicName;
    }
}
