package control;

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
            option = scanner.nextInt();
            selectOption(option);

        }while (option != 0);
        scanner.close();
    }

    private void selectOption(int option) {
        switch (option) {
            case 1:
                System.out.println("Enter the island: ");
                String island = new Scanner(System.in).nextLine();
                System.out.println("Enter the city: ");
                String city = new Scanner(System.in).nextLine();
                System.out.println("Enter the entry date (YYYY-MM-DD): ");
                String entryDate = new Scanner(System.in).nextLine();
                getDataView().viewHotelsByIslandCityAndDate(island, city, entryDate);
                break;
            case 2:
                System.out.println("Enter the hotel name: ");
                String hotelName = new Scanner(System.in).nextLine();
                System.out.println("Enter the entry date (YYYY-MM-DD): ");
                String entryDate2 = new Scanner(System.in).nextLine();
                getDataView().viewWeatherByHotelNameAndDate(hotelName, entryDate2);
                break;
            case 0:
                System.out.println("Bye");
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    void showMenu() {
        System.out.println("Welcome to the travel predictor");
        System.out.println("1. Search hotels by island, city and date of entry(YYYY-MM-DD)");
        System.out.println("Important: The hotels to be given are available up to 4 days after the check-in date.");
        System.out.println("2. Search prediction weather by hotel name and date of entry(YYYY-MM-DD)");
        System.out.println("0. Exit");
    }

    public DataView getDataView() {
        return dataView;
    }
}
