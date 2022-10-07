package ua.com.javarush.dhashuk.wonderisland.entity.predators;


import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.entity.Entity;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalFactory;

import java.util.Map;

public class Wolf extends Predator {

    public Wolf(double weight, int moveSpeed, double wellFed, Map<String,Integer> chanceToEat) {
        super(weight, moveSpeed, wellFed, chanceToEat);
    }

    @Override
    public boolean multiply(Animal animal) {
        if(super.multiply(animal)){
            getChildList().add(AnimalFactory.getAnimal(this.getClass()));
            return true;
        }
        return false;
    }
}
