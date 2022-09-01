package ua.com.javarush.dhashuk.wonderisland.entity.predators;

import ua.com.javarush.dhashuk.wonderisland.entity.Entity;

import java.util.Map;

public class Boa extends Predator{
    public Boa(double weight, int moveSpeed, double wellFed, Map<String,Integer> chanceToEat) {
        super(weight, moveSpeed, wellFed, chanceToEat);
    }
}
