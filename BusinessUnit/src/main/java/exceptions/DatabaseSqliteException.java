package exceptions;

public class DatabaseSqliteException extends RuntimeException{
    public DatabaseSqliteException(String message) {
        super(message);
    }
}
