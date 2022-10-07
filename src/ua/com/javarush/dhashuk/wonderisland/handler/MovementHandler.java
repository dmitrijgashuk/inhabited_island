package ua.com.javarush.dhashuk.wonderisland.handler;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.model.Area;

import java.util.Random;

public class MovementHandler {
    private Area[][] gameZones;

    public MovementHandler(Area[][] areas){
        this.gameZones = areas;
    }

    public boolean movingAlgorithm(Animal animal, Area area) { // true если удалось переместится
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

    private boolean checkGameZoneBorder(Area currentField, int direction) { // move
        return switch (direction){
            case 0 -> currentField.getAXIS_X() - 1 != -1;
            case 1 -> currentField.getAXIS_Y() - 1 != -1;
            case 2 -> currentField.getAXIS_X() + 1 < gameZones.length; // !=
            case 3 -> currentField.getAXIS_Y() + 1 < gameZones[1].length; //!=
            default -> false;
        };
    }

    private Area getNewDestination(Area currentField, int direction) { //move
        return switch (direction) {
            case 0 -> gameZones[currentField.getAXIS_X() - 1][currentField.getAXIS_Y()];
            case 1 -> gameZones[currentField.getAXIS_X()][currentField.getAXIS_Y() - 1];
            case 2 -> gameZones[currentField.getAXIS_X() + 1][currentField.getAXIS_Y()];
            case 3 -> gameZones[currentField.getAXIS_X()][currentField.getAXIS_Y() + 1];
            default -> null;
        };
    }
}
