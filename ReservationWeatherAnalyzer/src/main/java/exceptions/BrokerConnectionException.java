package exceptions;

public class BrokerConnectionException extends RuntimeException{
    public BrokerConnectionException(String message) {
        super(message);
    }
}
