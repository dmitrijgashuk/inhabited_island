package ua.com.javarush.dhashuk.wonderisland.model;

import lombok.Getter;
import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Rabbit;
import ua.com.javarush.dhashuk.wonderisland.entity.predators.Wolf;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalFactory;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalsClassName;
import ua.com.javarush.dhashuk.wonderisland.handler.configurator.Configurator;

import java.util.HashMap;
import java.util.Map;

public class Island {

    @Getter
    private Area[][] gameZones;

    public Island(){
        initializationFromProperties();
    }

    private void initializationFromProperties(){
        Configurator config = Configurator.getConfig();
        int maxWidth = config.getIslandSize("maxWidth");
        int maxHeight = config.getIslandSize("maxHeight");
        gameZones = new Area[maxWidth][maxHeight];

            for (int i = 0; i < gameZones.length; i++) {
                for (int j = 0; j < gameZones[i].length; j++) {

                    Area field = new Area(i,j);
                    gameZones[i][j] = field;

                    Map<String, Integer> startNumberOfAnimals = config.getStartNumberOfAnimals();
                    for(var animal : startNumberOfAnimals.entrySet()){
                        for (int k = 0; k < animal.getValue(); k++) {
                            String key = animal.getKey();
                            try {
                                String stringClazzName = key.toUpperCase();
                                AnimalsClassName fullClassName = AnimalsClassName.valueOf(stringClazzName);
                                Class<?extends Animal> aClass = (Class<? extends Animal>) Class.forName(fullClassName.getClassName());

                                field.getAnimals().add(AnimalFactory.getAnimal(aClass));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();//todo
                            }
                        }
                    }
                }
            }
        }

    @Deprecated
    private void defaultInitialization(){
        gameZones =new Area[5][5];

        for (int i = 0; i < gameZones.length; i++) {
            for (int j = 0; j < gameZones[i].length; j++) {
                // инициализация полей и добавление животных в игровые клетки

                Area field = new Area(i,j);// одно поле на клетку
                gameZones[i][j] = field;

                //  todo фабрику можно добавить через композицию либо агрегаци
                //   или создать отдельный клас инициализации
                Animal wolf = AnimalFactory.getAnimal(Wolf.class); // получение животного через фабрику
                Animal wolf1 = AnimalFactory.getAnimal(Wolf.class); // получение животного через фабрику
                field.getAnimals().add(wolf);
                field.getAnimals().add(wolf1);
                for (int k=0; k<10; k++){
                    AnimalFactory animalFactory = new AnimalFactory();
                    // set mapProperties
                    field.getAnimals().add(AnimalFactory.getAnimal(Rabbit.class));
                }
            }
        }
    }

}
