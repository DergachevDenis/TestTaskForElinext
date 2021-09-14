package by.dergachev.exceptions;

public class ConstructorNotFoundException extends RuntimeException{
    public ConstructorNotFoundException() {
    }

    public ConstructorNotFoundException(String message) {
        super(message);
    }
}
