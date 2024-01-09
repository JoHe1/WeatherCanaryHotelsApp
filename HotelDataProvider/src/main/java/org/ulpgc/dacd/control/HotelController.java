package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Hotel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.ulpgc.dacd.control.Main.mapIslandLocation;

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
    public void periodicTask() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        Calendar nextRun = Calendar.getInstance();
        nextRun.set(Calendar.HOUR_OF_DAY, 12);
        nextRun.set(Calendar.MINUTE, 00);
        nextRun.set(Calendar.SECOND, 0);
        nextRun.set(Calendar.MILLISECOND, 0);
        Date firstRun = nextRun.getTime();
        long initialDelay = firstRun.getTime() - System.currentTimeMillis();
        scheduler.scheduleAtFixedRate(new Task(this), initialDelay, 4 * 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        new Task(this).run(); // TODO remove
    }
}
