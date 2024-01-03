package exceptions;

public class JmsExceptionConection extends RuntimeException{
    public JmsExceptionConection(String message, Throwable cause) {
        super(message, cause);
    }
}
