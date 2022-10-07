package ua.com.javarush.dhashuk.wonderisland.factory;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;;
import ua.com.javarush.dhashuk.wonderisland.handler.configurator.Configurator;
import ua.com.javarush.dhashuk.wonderisland.log.WonderLog;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class AnimalFactory {
    private static WonderLog LOG = WonderLog.getInstance();

    private static String CREATE_EXCEPTION_MESSAGE = "Something has happened, %s instance did not create.";

    public static Animal getAnimal(Class<? extends Animal> clazz){
        Animal animal;
        try {
            var declaredConstructor = clazz.getDeclaredConstructor(double.class, int.class, double.class, Map.class);

            Configurator config = Configurator.getConfig();
            Configurator.AnimalConfigData animalParameters = config.getAnimalParameters(clazz.getSimpleName());

            double weight = animalParameters.getWeight();
            Integer speed = animalParameters.getMaxMovementPoints();
            double feedAmount = animalParameters.getMaxFeeding();
            Map<String,Integer> likelyToEat = animalParameters.getLikelyToEat();

            animal = declaredConstructor.newInstance(weight,speed,feedAmount,likelyToEat);

        } catch (IllegalArgumentException |InvocationTargetException | NoSuchMethodException |InstantiationException | IllegalAccessException exception) {
            LOG.error(CREATE_EXCEPTION_MESSAGE);
            String stringFormat = String.format(CREATE_EXCEPTION_MESSAGE, clazz.getSimpleName());
            throw new CreateAnimalException(stringFormat,exception.getCause());
        }
        return animal;
    }

}
