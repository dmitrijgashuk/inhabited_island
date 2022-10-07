package ua.com.javarush.dhashuk.wonderisland;

import ua.com.javarush.dhashuk.wonderisland.entity.Animal;
import ua.com.javarush.dhashuk.wonderisland.handler.FeedingHandler;
import ua.com.javarush.dhashuk.wonderisland.handler.MovementHandler;
import ua.com.javarush.dhashuk.wonderisland.handler.ReproductionHandler;
import ua.com.javarush.dhashuk.wonderisland.model.Area;
import ua.com.javarush.dhashuk.wonderisland.model.Island;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Stream;

public class DayProcessor {
    private Area[][] gameZones;

    private Queue<Area> areaQueue;

    public DayProcessor(Island inhabitedIsland) {
        this.gameZones = inhabitedIsland.getGameZones();
    }

    public void startNewDay() {
        ReproductionHandler reproductionHandler = new ReproductionHandler(gameZones);
        MovementHandler movementHandler = new MovementHandler(gameZones);
        FeedingHandler feedingHandler = new FeedingHandler(gameZones);

        for (int j = 0; j < gameZones.length; j++) {
            for (int k = 0; k < gameZones[j].length; k++) {

                Area currentArea = gameZones[j][k];

                reproductionHandler.multiplyAlgorithm(currentArea);

                List<Animal> unmodifiedList = List.copyOf(currentArea.getAnimals());
                for (Animal animal : unmodifiedList) {
                    movementHandler.movingAlgorithm(animal, currentArea);
                }
                feedingHandler.eatingAlgorithm(currentArea);
            }
        }
    }

    public void concurrentNewDay() {

        initQueue();

        ReproductionHandler reproductionHandler = new ReproductionHandler(gameZones);
        MovementHandler movementHandler = new MovementHandler(gameZones);
        FeedingHandler feedingHandler = new FeedingHandler(gameZones);

        // dodaty - zabraty

        new Thread(new Runnable() {
            @Override
            public void run() {
                Area currentArea = areaQueue.poll();
                reproductionHandler.multiplyAlgorithm(currentArea);

                List<Animal> unmodifiedList = List.copyOf(currentArea.getAnimals());
                for (Animal animal : unmodifiedList) {
                    movementHandler.movingAlgorithm(animal, currentArea);
                }
                feedingHandler.eatingAlgorithm(currentArea);
            }
        }).start();

    }

    private void initQueue() {
        areaQueue = new ConcurrentLinkedQueue<Area>();

        Stream<Area> stream = Arrays.stream(gameZones).flatMap(new Function<Area[], Stream<Area>>() {
            @Override
            public Stream<Area> apply(Area[] areas) {
                return Arrays.stream(areas);
            }
        });

        stream.forEach(x -> areaQueue.add((Area) x));

    }
}
