package ua.com.javarush.dhashuk.wonderisland.entity.herbivorous;

import ua.com.javarush.dhashuk.wonderisland.entity.Entity;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalFactory;
import ua.com.javarush.dhashuk.wonderisland.entity.Animal;

import java.util.Map;

public class Rabbit extends Herbivorous {

    public Rabbit(double weight, int moveSpeed, double wellFed, Map<String,Integer> chanceToEat) {
        super(weight, moveSpeed, wellFed, chanceToEat);
    }

    @Override
    public boolean multiply(Animal animal) {
        if(super.multiply(animal)){
            getChildList().add(AnimalFactory.getAnimal(this.getClass()));
            getChildList().add(AnimalFactory.getAnimal(this.getClass()));
            return true;
        }
        return false;
    }

}
