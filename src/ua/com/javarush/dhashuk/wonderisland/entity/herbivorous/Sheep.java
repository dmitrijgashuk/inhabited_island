package ua.com.javarush.dhashuk.wonderisland.entity.herbivorous;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.entity.Entity;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Sheep extends Herbivorous{
    public Sheep(double weight, int moveSpeed, double wellFed, Map<String,Integer> chanceToEat) {
        super(weight, moveSpeed, wellFed, chanceToEat);
    }
}
