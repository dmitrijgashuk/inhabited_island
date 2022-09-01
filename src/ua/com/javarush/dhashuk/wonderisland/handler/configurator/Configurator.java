package ua.com.javarush.dhashuk.wonderisland.handler.configurator;

import lombok.Data;
import ua.com.javarush.dhashuk.wonderisland.entity.Animal;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Configurator {

    private static Configurator config;

    private  Map<String,Integer> maxAnimalsInTheField = new HashMap<>();

    private Map<String,Integer> islandSize;

    private Map<String,AnimalConfigData> animalParameters = new HashMap();

    private Map<String,Integer> startNumberOfAnimals;

    private Map<String,Map<String,Integer>> likelyToEat = new HashMap<>();

    private Configurator(){

    }

    public static Configurator getConfig(){
        if(config == null){
            config = new Configurator();
        }
        return config;
    }

    public void readProperties(String fileName){
        PropertiesReader reader = new PropertiesReader();

        boolean isJson = fileName.endsWith(".json");
        if (isJson) {
            Path filePath = Path.of(fileName);
            Map<String, Object> inputParametersMap = reader.readJsomFileProperties(filePath);

            this.islandSize = (Map<String, Integer>) inputParametersMap.get("size");

            Map<String, Object> animals = (Map<String, Object>) inputParametersMap.get("Animals");

            for (var animal: animals.entrySet()) {
                String keyClass = animal.getKey();
                Map<String, Object> value = (Map<String, Object>) animal.getValue();
                Integer maxAmount = (Integer) value.get("maxAmount");
                maxAnimalsInTheField.put(keyClass, maxAmount);

                AnimalConfigData animalConfigData = new AnimalConfigData();

                animalConfigData.setWeight((Double) value.get("weight"));
                animalConfigData.setMaxFeeding((Double) value.get("feedAmount"));
                animalConfigData.setMaxMovementPoints((Integer) value.get("speed"));
                animalConfigData.setLikelyToEat((Map<String, Integer>) value.get("likelyToEat"));

                animalParameters.put(keyClass,animalConfigData);
            }

            this.startNumberOfAnimals = (Map<String, Integer>) inputParametersMap.get("startNumberOfAnimals");

        }
    }

    public int getMaxAnimalsInTheField (Class<? extends Animal> animal){
        return maxAnimalsInTheField.get(animal.getSimpleName());
    }

    public int getIslandSize(String sizeName){
        return islandSize.get(sizeName);
    }

    public AnimalConfigData getAnimalParameters(String animal){
        return animalParameters.get(animal);
    }

    public Map<String,Integer> getStartNumberOfAnimals(){
        return  startNumberOfAnimals;
    }

    @Data
    public static class AnimalConfigData {
        private double weight;
        private int maxMovementPoints;
        private double maxFeeding;
        private Map<String,Integer> likelyToEat;
    }

}
