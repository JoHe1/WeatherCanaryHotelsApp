package org.ulpgc.dacd.control;

import java.util.Date;

public class Task implements Runnable{
    private final HotelController hotelController;

    public Task(HotelController hotelController) {
        this.hotelController = hotelController;
    }

    @Override
    public void run() {
        System.out.println("The task execute at: " + new Date());
        getHotelController().execute();
    }

    public HotelController getHotelController() {
        return hotelController;
    }
}
