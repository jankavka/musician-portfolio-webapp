package cz.kavka.service.exception;

public class WrongContentTypeException extends RuntimeException{

    public WrongContentTypeException(String message){
        super(message);
    }
}
