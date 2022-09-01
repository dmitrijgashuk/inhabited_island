package ua.com.javarush.dhashuk.wonderisland.handler.statistic;

import ua.com.javarush.dhashuk.wonderisland.model.Area;
import ua.com.javarush.dhashuk.wonderisland.entity.Animal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticHandler {

    private Area[][] gameZones;

    private Map<Class<? extends Animal>, Integer> cash = new ConcurrentHashMap<>();

    public StatisticHandler(Area[][] gameZones) {
        this.gameZones = gameZones;
    }

    public void showAnimalAmount(){

        HashMap<Class<? extends Animal>, Integer> allAnimalsMap = Arrays.stream(gameZones).flatMap(new Function<Area[], Stream<List<Animal>>>() {
            @Override
            public Stream<List<Animal>> apply(Area[] areas) {
                Stream<List<Animal>> listStream = Arrays.stream(areas).map(area -> area.getAnimals());
                return listStream;
            }
        }).flatMap(new Function<List<Animal>, Stream<Animal>>() {
            @Override
            public Stream<Animal> apply(List<Animal> animals) {
                Stream<Animal> stream = animals.stream();
                return stream;
            }
        }).collect(HashMap::new, new BiConsumer<HashMap<Class<? extends Animal>, Integer>, Animal>() {
            @Override
            public void accept(HashMap<Class<? extends Animal>, Integer> hashMap, Animal animal) {
                if (hashMap.containsKey(animal.getClass())) {
                    Integer amount = hashMap.get(animal.getClass()) + 1;
                    hashMap.put(animal.getClass(), amount);
                } else {
                    hashMap.put(animal.getClass(), 1);
                }
            }
        }, HashMap::putAll);

        allAnimalsMap.forEach(new BiConsumer<Class<? extends Animal>, Integer>() {
            @Override
            public void accept(Class<? extends Animal> aClass, Integer integer) {

                System.out.println("Animal - " + aClass.getSimpleName() + ": "
                        + integer + "(" + (integer - getCashValue(aClass) ) + ")");
            }
        });

        cash = Map.copyOf(allAnimalsMap);
    }

    private int getCashValue(Class<? extends Animal> aClass){
        if(cash.isEmpty()){
            return 0;
        } else {
            return cash.get(aClass);
        }
    }

}
