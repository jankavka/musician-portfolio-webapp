package cz.kavka.service.exception.message;

public class ExceptionMessage {

    private ExceptionMessage(){

    }

    public static String entityNotFoundExceptionMessage(String object, Long id){
        return object + " s id " + id + " nebyl nalezen";
    }

    public static String USER_NOT_FOUND ="Uživatel nenalezen";

}
