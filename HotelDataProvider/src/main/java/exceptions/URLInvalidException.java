package exceptions;

public class URLInvalidException extends RuntimeException{
    public URLInvalidException(String message) {
        super(message);
    }
}
