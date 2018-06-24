package exception;

public class DataErrorException extends Exception {
    public DataErrorException() {
        super();
    }

    public DataErrorException(String message) {
        super(message);
    }

    DataErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    DataErrorException(Throwable cause) {
        super(cause);
    }
}
