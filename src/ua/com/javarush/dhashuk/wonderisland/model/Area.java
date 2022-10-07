package ua.com.javarush.dhashuk.wonderisland.model;

import lombok.Getter;
import lombok.Setter;
import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.handler.configurator.Configurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Area {
    @Getter
    private List<Animal> animals;

    private final double MAX_AMOUNT_OF_GRASS;
    @Getter
    private double currentAmountOfGrass;

    @Getter
    private final int AXIS_X;
    @Getter
    private final int AXIS_Y;

    public Area(int i, int j) {
        MAX_AMOUNT_OF_GRASS = 200;
        animals = new ArrayList<>();
        AXIS_X = i;
        AXIS_Y = j;
        currentAmountOfGrass = MAX_AMOUNT_OF_GRASS;
    }

    public void setCurrentAmountOfGrass(double plants) {
        if(plants<=MAX_AMOUNT_OF_GRASS){
            this.currentAmountOfGrass = plants;
        }else {
            currentAmountOfGrass = MAX_AMOUNT_OF_GRASS;
        }
    }

    public boolean checkAnimalLimit(Animal animal){
        Class<? extends Animal> AnimalClazz = animal.getClass();
        Configurator config = Configurator.getConfig();
        return config.getMaxAnimalsInTheField(AnimalClazz) > amountAnimalOfType(AnimalClazz);
    }

    private int amountAnimalOfType(Class<? extends Animal> type){
        return (int) animals.stream().filter((animal) -> {
                if (animal.getClass() == type) {
                    return true;
                }
                return false;
            }).count();
    }
}
