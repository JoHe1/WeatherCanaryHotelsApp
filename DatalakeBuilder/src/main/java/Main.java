public class Main {
    public static void main(String[] args) { // TODO PASAR POR ARGUMENTOS LA RUTA DELFICHERO DATALAKE hacer
        Storable hotelStorable = new HotelStorable(args[0]);
        Storable WeatherStorable = new WeatherStorable(args[0]);
        hotelStorable.save();
        WeatherStorable.save();
    }
}
