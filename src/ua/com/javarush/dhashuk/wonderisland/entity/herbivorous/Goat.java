package ua.com.javarush.dhashuk.wonderisland.entity.herbivorous;

import ua.com.javarush.dhashuk.wonderisland.entity.Entity;

import java.util.Map;

public class Goat extends Herbivorous{

    public Goat(double weight, int moveSpeed, double wellFed, Map<String,Integer> chanceToEat) {
        super(weight, moveSpeed, wellFed, chanceToEat);
    }
}
