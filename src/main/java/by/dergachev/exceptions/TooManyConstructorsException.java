package by.dergachev.exceptions;

public class TooManyConstructorsException extends RuntimeException {
    public TooManyConstructorsException() {
    }

    public TooManyConstructorsException(String message) {
        super(message);
    }
}
