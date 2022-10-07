package ua.com.javarush.dhashuk.wonderisland;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.entity.predators.Predator;
import ua.com.javarush.dhashuk.wonderisland.entity.predators.Wolf;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalFactory;
import ua.com.javarush.dhashuk.wonderisland.handler.configurator.Configurator;
import ua.com.javarush.dhashuk.wonderisland.model.Area;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class DemoIsland {

    static Area[][] gameZones = new Area[30][30];

    public static void main(String[] args) {

        Configurator config = Configurator.getConfig();
        config.readProperties("parameter.json");


        for (int i = 0; i < gameZones.length; i++) {
            for (int j = 0; j < gameZones[i].length; j++) {
                gameZones[i][j] = new Area(i,j);
            }
        }

        Area field = new Area(1, 2);
        gameZones[1][2] = field;
        /*for (int k=0; k<10; k++){
            field.getAnimals().add(AnimalFactory.getAnimal("Rabbit"));
        }*/
        for (int i = 0; i <5; i++) {
            field.getAnimals().add(AnimalFactory.getAnimal(Wolf.class));
        }

        System.out.println(field.getAnimals().size());

        for (int i = 1; i < 4; i++) {

            System.out.println("Day: "+i);
            System.out.println("----------------------------");
            statistic();

            dayTact();
        }
    }

    private static void dayTact() {
        for (int j = 0; j < gameZones.length; j++) {
            for (int k = 0; k < gameZones[j].length; k++) {

                Area currentArea = gameZones[j][k];

                multiplyAlgorithm(currentArea); //1

                List<Animal> unmodifiedList = List.copyOf(currentArea.getAnimals());
                for (Animal animal : unmodifiedList) {
                    movingAlgorithm(animal,currentArea); //2
                }

                eatingAlgorithm(currentArea); //3
            }
        }
    }

    public static boolean movingAlgorithm(Animal animal, Area area) { // true если удалось переместится
        Area currentField = area; // создаем копию ссылки чтобы потом поменять ее на поле с новыми координатами
        boolean isMove = false;

        while (animal.isMove()) { // пока есть очки движения
            int direction = new Random().nextInt(4);// рандомное направление движения

            boolean withinTheBorders = checkGameZoneBorder(currentField, direction);

            if(withinTheBorders) {
                Area destinationField = getNewDestination(currentField, direction);
                animal.move();
                if (!destinationField.checkAnimalLimit(animal)) {
                    continue;
                }
                destinationField.getAnimals().add(animal);
                currentField.getAnimals().remove(animal);// удадение при итерации колекции может стать проблемой
                currentField = destinationField;
            }
        }
        return isMove;
    }

    private static boolean checkGameZoneBorder(Area currentField, int direction) { // move
        return switch (direction){
            case 0 -> currentField.getAXIS_X() - 1 != -1;
            case 1 -> currentField.getAXIS_Y() - 1 != -1;
            case 2 -> currentField.getAXIS_X() + 1 < gameZones.length; // !=
            case 3 -> currentField.getAXIS_Y() + 1 < gameZones[1].length; //!=
            default -> false;
        };
    }

    private static Area getNewDestination(Area currentField, int direction) { //move
        return switch (direction) {
            case 0 -> gameZones[currentField.getAXIS_X() - 1][currentField.getAXIS_Y()];
            case 1 -> gameZones[currentField.getAXIS_X()][currentField.getAXIS_Y() - 1];
            case 2 -> gameZones[currentField.getAXIS_X() + 1][currentField.getAXIS_Y()];
            case 3 -> gameZones[currentField.getAXIS_X()][currentField.getAXIS_Y() + 1];
            default -> null;
        };
    }

    private static void statistic(Area field) {
        Map<Class<? extends Animal>, Integer> amountOfAnimals = new HashMap<>();

        for (Animal anima : field.getAnimals()) {
            if (amountOfAnimals.containsKey(anima.getClass())) {
                Integer amount = amountOfAnimals.get(anima.getClass()) + 1;
                amountOfAnimals.put(anima.getClass(), amount);
            } else {
                amountOfAnimals.put(anima.getClass(), 1);
            }
        }
        amountOfAnimals.forEach((x, y) -> System.out.println("Animal - " + x.getSimpleName() + ": " + y));
    }

    private static void multiplyAlgorithm(Area field) {
        List<Animal> newChildrenList = new ArrayList<>();
        for (Animal parentOne : field.getAnimals()) {
            field.getAnimals()
                    .stream()
                    .filter((parentTwo) -> field.checkAnimalLimit(parentTwo))
                    .forEach(new Consumer<Animal>() {
                        @Override
                        public void accept(Animal parentTwo) {
                            boolean isReproduction = parentOne.multiply(parentTwo);
                            if (isReproduction) {
                                newChildrenList.addAll(parentOne.getChildList());
                                parentOne.setHasAChild(true);
                            }
                        }
                    });
        }
        for (Animal animal : newChildrenList) {
            if(field.checkAnimalLimit(animal)){
                field.getAnimals().add(animal);
            }
        }
    }

    private static void statistic() {
        Map<Class<? extends Animal>, Integer> amountOfAnimals = new HashMap<>();

        for (int i = 0; i < gameZones.length; i++) {
            for (int j = 0; j < gameZones[i].length; j++) {

                Area field = gameZones[i][j];

                List<Animal> animas = field.getAnimals();

                for (Animal anima : animas) {
                    if (amountOfAnimals.containsKey(anima.getClass())) {
                        Integer amount = amountOfAnimals.get(anima.getClass()) + 1;
                        amountOfAnimals.put(anima.getClass(), amount);
                    } else {
                        amountOfAnimals.put(anima.getClass(), 1);
                    }
                }

            }
        }

        amountOfAnimals.forEach((x, y) -> System.out.println("Animal - " + x.getSimpleName() + ": " + y));
    }

    private static void eatingAlgorithm(Area area) {
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




