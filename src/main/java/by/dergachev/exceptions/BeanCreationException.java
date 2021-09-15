package by.dergachev.exceptions;

public class BeanCreationException extends RuntimeException{
    public BeanCreationException() {
    }

    public BeanCreationException(String message) {
        super(message);
    }
}
