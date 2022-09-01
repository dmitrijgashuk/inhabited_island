package ua.com.javarush.dhashuk.wonderisland.entity.predators;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.entity.Entity;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {

    public Predator(double weight, int moveSpeed, double wellFed, Map<String,Integer> chanceToEat) {
        super(weight, moveSpeed, wellFed, chanceToEat);
    }

    @Override
    public double eat(Animal animal) {
        var chanceToEat = getChanceToEat();
        int chance = chanceToEat.get(animal.getClass().getSimpleName());
        int random = ThreadLocalRandom.current().nextInt(100);
        if(random<=chance){
            double meal = animal.getWeight();
            this.setCurrentFeeding(meal);
            animal.setAlive(false);
            return -1;
        }
        return 0;
    }
}
