package ua.com.javarush.dhashuk.wonderisland.entity.herbivorous;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivorous extends Animal {

    public Herbivorous(double weight, int moveSpeed, double wellFed, Map<String,Integer> chanceToEat) {
        super(weight, moveSpeed, wellFed, chanceToEat);
    }

    @Override
    public double eat(Animal animal) {
        //if( animal instansof гусеница) - > тогда проверяем может ли сьесть по списку
        // если нет то смело едим траву
        boolean checkCaterpillar = animal instanceof Caterpillar;
        if(checkCaterpillar) {
            var chanceToEat = getChanceToEat();
            int chance = chanceToEat.get(animal.getClass().getSimpleName());
            int random = ThreadLocalRandom.current().nextInt(100);
            if (random <= chance) { // если выпало меньше чесло чем вероятность, то сьели
                //System.out.println(this.getClass().getSimpleName() + " eats " + animal.getClass());
                double meal = animal.getWeight();
                this.setCurrentFeeding(meal);
                animal.setAlive(false);
                return -1; // сьел гусеницу
            }
        }
        //System.out.println("Это не гусеница, ем траву");
        double meal = this.getWeight() / 100 * 5;// естр траву а как ее уменчить на уровне выше?
        this.setCurrentFeeding(meal);
        return meal; // возвращает сколько сьел травы - хотя нужно посчитать сколько и есть ли столько травы?
    }
}
