package ua.com.javarush.dhashuk.wonderisland.handler;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.model.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReproductionHandler {
    private Area[][] gameZones;

    public ReproductionHandler(Area[][] areas){
        this.gameZones = areas;
    }

    public void multiplyAlgorithm(Area field) {
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
}
