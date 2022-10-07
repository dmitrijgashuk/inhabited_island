package ua.com.javarush.dhashuk.wonderisland;


import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Rabbit;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalFactory;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalsClassName;
import ua.com.javarush.dhashuk.wonderisland.handler.configurator.Configurator;
import ua.com.javarush.dhashuk.wonderisland.handler.configurator.PropertiesReader;


import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TestCase {

    public static void main(String[] args) {

        Configurator config = Configurator.getConfig();
        config.readProperties("parameter.json");

        Map<String, Integer> startNumberOfAnimals = config.getStartNumberOfAnimals();
        for(var animal : startNumberOfAnimals.entrySet()){
            for (int k = 0; k < animal.getValue(); k++) {
                String key = animal.getKey();
                try {
                    String stringClazzName = key.toUpperCase();
                    AnimalsClassName fullClassName = AnimalsClassName.valueOf(stringClazzName);
                    //Class<?extends Animal> aClass = (Class<? extends Animal>) Class.forName(fullClassName.getClassName());
                    Class<?> aClass = Class.forName(fullClassName.getClassName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();//todo
                }
            }
        }


        //Class<?extends Animal> aClass = (Class<? extends Animal>) Class.forName(key);


    }


}
