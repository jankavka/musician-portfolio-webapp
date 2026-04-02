package cz.kavka.service.exception;

public class NoCurrentUserException extends RuntimeException{

    public NoCurrentUserException(String message){
        super(message);
    }
}
