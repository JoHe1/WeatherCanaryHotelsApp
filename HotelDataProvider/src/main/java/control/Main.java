package control;

import model.Location;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Main {
        static Map<String, Location> mapIslandLocation = new HashMap<>();
        public static void main(String[] args) {
            HotelProvider hotelProvider = new SerpApiHotelProvider(args[0]);
            HotelStore hotelStore = new JmsHotelStore();
            HotelController hotelController = new HotelController(hotelProvider, hotelStore);
            loadStaticIslands(args[1]);
            hotelController.periodicTask();

        }

        private static void loadStaticIslands(String file) {
                try (Reader reader = new FileReader(file);
                     CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
                        for (CSVRecord csvRecord : csvParser) {
                                mapIslandLocation.put(csvRecord.get(0), new Location(csvRecord.get(0), csvRecord.get(1), csvRecord.get(2), csvRecord.get(3)));
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
