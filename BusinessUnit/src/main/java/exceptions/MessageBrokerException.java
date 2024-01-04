package exceptions;

public class MessageBrokerException extends RuntimeException {
    public MessageBrokerException(String message) {
        super(message);
    }
}
