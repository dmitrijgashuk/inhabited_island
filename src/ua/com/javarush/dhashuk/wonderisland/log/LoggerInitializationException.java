package ua.com.javarush.dhashuk.wonderisland.log;

public class LoggerInitializationException extends RuntimeException{

    public LoggerInitializationException(String message) {
        super(message);
    }

    public LoggerInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
