package cz.kavka.service.exception;

public class WrongVideoSourceException extends RuntimeException{

    public WrongVideoSourceException(String message){
        super(message);
    }
}
