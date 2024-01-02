package control;

import model.Hotel;

import java.util.List;

import static control.Main.mapIslandLocation;

public class HotelController {
    private final HotelProvider hotelProvider;
    private final HotelStore hotelStore;

    public HotelController(HotelProvider hotelProvider, HotelStore hotelStore) {
        this.hotelProvider = hotelProvider;
        this.hotelStore = hotelStore;
    }
    public HotelProvider getHotelDataProvider() {
        return hotelProvider;
    }

    public HotelStore getHotelDataStore() {
        return hotelStore;
    }
    void execute() {
        mapIslandLocation.forEach((island,location) -> {
            try {
                List<Hotel> hotels = getHotelDataProvider().get(location);
                if (hotels != null) {
                    for (Hotel hotel : hotels) {
                        getHotelDataStore().save(hotel);
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
