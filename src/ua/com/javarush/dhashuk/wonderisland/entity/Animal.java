package ua.com.javarush.dhashuk.wonderisland.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ua.com.javarush.dhashuk.wonderisland.factory.AnimalFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@EqualsAndHashCode
public abstract class Animal extends Entity{
    @Getter
    private double weight;
    @Getter
    private int maxMovementPoints;
    @Getter
    private double maxFeeding;

    private int currentMovementPoints;

    @Getter
    private double currentFeeding;

    @Getter
    private Map<String,Integer> chanceToEat; //шанс съесть другое животное

    @Getter @Setter
    private boolean isAlive; // состояние живо ли животное или нет
    @Getter @Setter
    private boolean hasAChild; // есть детеныши
    @Getter
    private List<Animal> childList = new ArrayList<>();


    public Animal(double weight, int moveSpeed, double wellFed, Map<String,Integer> chanceToEat) {
        this.weight = weight;
        this.maxFeeding = wellFed;
        this.maxMovementPoints = moveSpeed;
        this.chanceToEat = chanceToEat;
        currentMovementPoints = maxMovementPoints;
        currentFeeding = this.maxFeeding;
        isAlive = true;
        hasAChild = false;
    }

    public boolean move(){
        if(isMove()){
            currentMovementPoints--;
            currentFeeding -= (weight/100)*1; // сытость уменьшается при ходьбе, больше двигаемся быстрее тратим
            return true;
        }
        return false;
    }

    public abstract double eat(Animal animal);

    public boolean multiply(Animal animal) {// возвращает true если пара животных дали потомство
        if (this == animal) return false;
        if (animal.getClass() != this.getClass()) return false;
        if (hasAChild) return false;
        return true;
    }

    public boolean isMove(){// может ли животное еще ходить
        if(currentMovementPoints>0) {
            return true;
        }
        return false;
    }


    public void setCurrentFeeding(double meal){
        double feed = currentFeeding + meal;
        if(feed>maxFeeding){// сравнение дробныйх чисел нужно проверить !!!
            currentFeeding = maxFeeding;
        }else {
            currentFeeding = feed;
        }
    }





}
