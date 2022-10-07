package ua.com.javarush.dhashuk.wonderisland.factory;

public class CreateAnimalException extends RuntimeException {
    public CreateAnimalException(String message) {
        super(message);
    }

    public CreateAnimalException(String message, Throwable cause) {
        super(message, cause);
    }
}
