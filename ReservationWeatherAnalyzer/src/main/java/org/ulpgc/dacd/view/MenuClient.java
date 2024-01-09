package org.ulpgc.dacd.view;

import org.ulpgc.dacd.control.DataView;
import org.ulpgc.dacd.model.Hotel;
import org.ulpgc.dacd.model.Prediction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuClient {
    private final DataView dataView;

    public MenuClient(DataView dataView) {
        this.dataView = dataView;
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            showMenu();
            showInformationHowUse();
            System.out.print(blue()+"Enter an option: "+reset());
            option = scanner.nextInt();
            selectOption(option);

        }while (option != 0);
        scanner.close();
    }

    private void selectOption(int option) {
        switch (option) {
            case 1:

                System.out.print(blue()+"Enter the island: "+reset());
                String island = new Scanner(System.in).nextLine();
                System.out.print(blue()+"Enter the city: "+reset());
                String city = new Scanner(System.in).nextLine();
                System.out.println(blue()+"Looking for hotels to book tomorrow until 4 days after..."+reset());
                String dateTomorrow = calculateDateTomorrow();
                String dateLimit = calculateDateLimit(dateTomorrow);
                List<Hotel> hotels = getDataView().viewHotelsByIslandCityAndDate(island, city, dateTomorrow, dateLimit);
                responseHotel(hotels, island, city, dateTomorrow);
                break;
            case 2:
                System.out.print(blue()+"Enter the hotel name: "+reset());
                String hotelName = new Scanner(System.in).nextLine();
                System.out.println(blue()+"Searching predictions... "+reset());
                String dateTomorrow2 = calculateDateTomorrow();
                List<Prediction> predictions = getDataView().viewWeatherByHotelNameAndDate(hotelName, dateTomorrow2);
                responsePrediction(predictions, dateTomorrow2);
                break;
            case 0:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private String calculateDateTomorrow() {
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTomorrowString = dateTomorrow.format(formatter);
        return dateTomorrowString;
    }

    private void responsePrediction(List<Prediction> predictions, String entryDate2) {
        String dateLimit = calculateDateLimit(entryDate2);
        if (dateLimit == null) {
            return;
        }
        if(predictions == null){
            System.out.println(red()+"Predictions not found or some parameter has been entered incorrectly"+reset());
        } else {
            System.out.println("|---------------- " + bold() + "Weather prediction for " + reset() + green() + predictions.get(0).getTown() + "," + predictions.get(0).getIsland() + " from " + entryDate2 + " to " + dateLimit + reset() + " -------------");
            for(Prediction prediction : predictions){
                System.out.print(
                        "| " + bold() + "Prediction date: " + reset() + red() + prediction.getPredictionTime() + reset() + "\n" +
                                "| " + bold() + "Temperature: " + reset() + red() + prediction.getTemperature() + reset() + " Celsius\n" +
                                "| " + bold() + "Humidity: " + reset() + red() + prediction.getHumidity() + reset() + " %\n" +
                                "| " + bold() + "Wind: " + reset() + red() + prediction.getWind_velocity() + reset() + " m/s\n" +
                                "| " + bold() + "Precipitation: " + reset() + red() + prediction.getPrecipitation() + reset() + " %\n" +
                                "| " + bold() + "Clouds: " + reset() + red() + prediction.getCloud() + reset() + " %\n"
                );
                System.out.println("|------------------------------------------------------------------");
            }
        }
    }

    private void responseHotel(List<Hotel> hotels, String island, String city, String entryDate) {
        if(hotels == null) {
            System.out.println(red()+"Hotels not found or some parameter has been entered incorrectly"+reset());
        } else {
            System.out.println("|---------------- " + bold() + "Hotels available in " + reset() + green() + city + "," + island + " on " + entryDate + reset() + " ---------------");
            for (Hotel hotel : hotels){
                System.out.print("|------------- " + blue() + hotel.getName() + reset() + " ---- Stars " + red() + hotel.getNumberOfStars() + reset() + " -------\n" +
                        "| " + bold() + "Description: " + reset() + red() + hotel.getDescription() + reset() + "\n" +
                        "| " + bold() + "Link: " + reset() + red() + hotel.getLink() + reset() + "\n"
                );
                
                if (Double.parseDouble(hotel.getPrice()) != 0.0) {
                    System.out.print("| " + bold() + "Price: " + reset() + red() + hotel.getPrice() + reset() + "\n");
                } else {
                    System.out.print("| " + bold() + "Price: " + reset() + red() + "Not available" + reset() + "\n");
                }
                System.out.print("| " + bold() + "Number of reviews: " + reset() + red() + hotel.getNumberOfReviews() + reset() + "\n");
                if (Double.parseDouble(hotel.getRating()) != 0.0) {
                    System.out.print("| " + bold() + "Rating: " + reset() + red() + hotel.getRating() + reset() + "\n");
                } else {
                    System.out.print("| " + bold() + "Rating: " + reset() + red() + "Not available" + reset() + "\n");
                }
                System.out.println("| " + bold() + "Check-in: " + reset() + red() + hotel.getCheckInDate() + reset() + " at " + red() + hotel.getCheckInTime() + reset());
                System.out.println("| " + bold() + "Check-out: " + reset() + red() + hotel.getCheckOutDate() + reset() + " at " + red() + hotel.getCheckOutTime() + reset());
            }
        }
    }

    void showInformationHowUse(){
        System.out.println("| --------------- "+bold()+"How to use the travel predictor "+reset()+"---------------");
        System.out.println("| This application will be giving you information on possible hotels starting tomorrow, updating every 4 days since that is how it works.");
        System.out.println("| The islands and their municipalities that you can search for are: \n" +
                "| La Palma - Santa Cruz de la Palma \n" +
                "| Tenerife - Santa Cruz de Tenerife \n" +
                "| La Gomera - San Sebastian de la Gomera \n" +
                "| El Hierro - Villa de Valverde \n" +
                "| Gran Canaria - Las Palmas de Gran Canaria \n" +
                "| Lanzarote - Arrecife \n" +
                "| Fuerteventura - Puerto del Rosario "
        );
        System.out.println("| --------------------------------------------------------");
    }

    void showMenu() {
        System.out.println("| --------------- "+bold()+"Welcome to the travel predictor "+reset()+"---------------");
        System.out.println("| 1. Search hotels by island and city");
        System.out.println("| Important: The hotels to be given are available up to 5 days from tomorrow.");
        System.out.println("| 2. Search prediction weather by hotel name");
        System.out.println("|"+red()+" 0. Exit"+reset());
        System.out.println("| --------------------------------------------------------");
    }

    private String calculateDateLimit(String entryDate) {
        LocalDate dateLimit = LocalDate.parse(entryDate, DateTimeFormatter.ISO_LOCAL_DATE);
        dateLimit = dateLimit.plusDays(4);
        String dateLimitString = dateLimit.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return dateLimitString;
    }

    public DataView getDataView() {
        return dataView;
    }

    public String bold() {
    	return "\u001B[1m";
    }
    public String reset() {
    	return "\u001B[0m";
    }
    public String red() {
    	return "\u001B[31m";
    }
    public String blue() {
    	return "\u001B[34m";
    }
    public String green() {
        return "\u001B[32m";
    }
}
