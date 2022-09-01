package ua.com.javarush.dhashuk.wonderisland.handler;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.entity.predators.Predator;
import ua.com.javarush.dhashuk.wonderisland.model.Area;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FeedingHandler {
    private Area[][] gameZones;

    public FeedingHandler(Area[][] areas){
        this.gameZones = areas;
    }

    public void eatingAlgorithm(Area area) {
        Area sell = area;

        List<Animal> animals = sell.getAnimals();
        for (Animal item : animals) {
            if (item.isAlive()) {// перебор животных на клетке
                boolean isEaten = false; // животное поело?
                // в случаейном порядке ищим поесть несколько раз допустим как количество ходов?
                // потом можно и очки ходов уменьшать, неполучится поесть будет голодным получится несколько раз молодец
                int attempt = 0;
                //todo добавить логику проверки ести оба хищники зачем дальше проверять?
                while (!isEaten && attempt <= item.getMaxMovementPoints()) { // попробуем еще если не поели
                    int animalIndex = ThreadLocalRandom.current().nextInt(animals.size());
                    Animal mealAnimal = animals.get(animalIndex);
                    if(item instanceof Predator && mealAnimal instanceof Predator){ //проверка на хищников
                        attempt++;
                        continue;
                    }
                    if (mealAnimal.isAlive()) {
                        double amountEatenPlants = item.eat(mealAnimal);
                        if (amountEatenPlants > 0) {
                            double amountOfPlants = sell.getCurrentAmountOfGrass() - amountEatenPlants;
                            sell.setCurrentAmountOfGrass(amountOfPlants);
                            isEaten = true;
                        } else if (amountEatenPlants == -1) {
                            isEaten = true;
                        }
                    }
                    attempt++;
                }

                // сытость уменьшается при охоте, больше двигаемся быстрее тратим
                item.setCurrentFeeding(item.getCurrentFeeding()-(item.getWeight()/100)*2);

                if(item.getCurrentFeeding()<=0){
                    item.setAlive(false); // если не смог поесть и сытость 0 то умер
                }
            }
        }

        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (!animal.isAlive()) {
                iterator.remove();
            }
        }
    }
}
