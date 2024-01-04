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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HotelDataProcessor implements DataProcessor{
    private final String datamartUrl; // TODO PASAR POR ARGUMENTO
    private final String datalakeUrl;
    private final String topicName = "information.Hotel";

    public HotelDataProcessor(String datamartUrl, String datalakeUrl) {
        this.datamartUrl = "jdbc:sqlite:" + datamartUrl + "datamart.db";
        this.datalakeUrl = datalakeUrl + "datalake/eventstore/information.Hotel/SerpApi/";
    }

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
        String directory = getDatalakeUrl();
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
        createHotelTable();
        List<Object> listHotelData = collectInformation(data);
        insertHotelData(listHotelData);
    }
    private void insertHotelData(List<Object> listHotelData) {
        try(Connection connection = DriverManager.getConnection(getDatamartUrl())) {
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO hotel VALUES (" +
                    "null," +
                    "'" + listHotelData.get(0) + "'," +
                    "'" + listHotelData.get(1) + "'," +
                    "'" + listHotelData.get(2) + "'," +
                    "'" + listHotelData.get(3) + "'," +
                    "'" + listHotelData.get(4) + "'," +
                    "'" + listHotelData.get(5) + "'," +
                    "'" + listHotelData.get(6) + "'," +
                    "'" + listHotelData.get(7) + "'," +
                    "'" + listHotelData.get(8) + "'," +
                    listHotelData.get(9) + "," +
                    listHotelData.get(10) + "," +
                    listHotelData.get(11) + "," +
                    listHotelData.get(12) +
                    ");"
            );
        } catch (SQLException e) {
            throw new DatabaseSqliteException(e.getMessage());
        }
    }
    private List<Object> collectInformation(String data) {
        ArrayList<Object> listHotelData = new ArrayList<>(); // TODO HACER UN MAP PARA QUE QUEDE MAS MONO ¿?
        JsonElement jsonElement = JsonParser.parseString(data);
        listHotelData.add(jsonElement.getAsJsonObject().get("name").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("location").getAsJsonObject().get("island").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("location").getAsJsonObject().get("town").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("description").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("link").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("checkInDate").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("checkInTime").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("checkOutDate").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("checkOutTime").getAsString());
        listHotelData.add(jsonElement.getAsJsonObject().get("price").getAsDouble());
        listHotelData.add(jsonElement.getAsJsonObject().get("rating").getAsDouble());
        listHotelData.add(jsonElement.getAsJsonObject().get("numberOfReviews").getAsInt());
        listHotelData.add(jsonElement.getAsJsonObject().get("numberOfStars").getAsInt());
        return listHotelData;
    }
    private void createHotelTable() {
        try(Connection connection = DriverManager.getConnection(getDatamartUrl())) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS hotel (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "island TEXT," +
                    "town TEXT," +
                    "description TEXT," +
                    "link TEXT," +
                    "checkInDate TEXT," +
                    "checkInTime TEXT," +
                    "checkOutDate TEXT," +
                    "checkOutTime TEXT," +
                    "price REAL," +
                    "rating REAL," +
                    "numberOfReviews INTEGER," +
                    "numberOfStars INTEGER" +
                    ");"
            );
        } catch (SQLException e) {
            throw new DatabaseSqliteException(e.getMessage());
        }
    }

    @Override
    public void collectDataFromBroker() {
        createHotelTable();
        MessageConsumer subscriber = initialiseSuscriber();
        try {
            subscriber.setMessageListener(message -> {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    List<Object> listHotelData = collectInformation(text);
                    insertHotelData(listHotelData);
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

    public String getDatalakeUrl() {
        return datalakeUrl;
    }
}