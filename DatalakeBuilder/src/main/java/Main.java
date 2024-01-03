public class Main {
    public static void main(String[] args) { // TODO PASAR POR ARGUMENTOS LA RUTA DELFICHERO DATALAKE¿?
        Storable hotelStorable = new HotelStorable();
        Storable WeatherStorable = new WeatherStorable();
        hotelStorable.save();
        WeatherStorable.save();
    }
}
