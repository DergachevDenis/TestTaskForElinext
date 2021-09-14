package by.dergachev.exceptions;

public class BindingNotFoundException extends RuntimeException{
    public BindingNotFoundException() {
    }

    public BindingNotFoundException(String message) {
        super(message);
    }

}
