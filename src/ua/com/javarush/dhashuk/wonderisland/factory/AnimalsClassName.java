package ua.com.javarush.dhashuk.wonderisland.factory;

public enum AnimalsClassName {
    BOAR("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Boar"),
    BUFFALO("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Buffalo"),
    CATERPILLAR("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Caterpillar"),
    DEER("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Deer"),
    DUCK("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Duck"),
    GOAT("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Goat"),
    HORSE("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Horse"),
    MOUSE("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Mouse"),
    RABBIT("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Rabbit"),
    SHEEP("ua.com.javarush.dhashuk.wonderisland.entity.herbivorous.Sheep"),
    BEAR("ua.com.javarush.dhashuk.wonderisland.entity.predators.Bear"),
    BOA("ua.com.javarush.dhashuk.wonderisland.entity.predators.Boa"),
    EAGLE("ua.com.javarush.dhashuk.wonderisland.entity.predators.Eagle"),
    FOX("ua.com.javarush.dhashuk.wonderisland.entity.predators.Fox"),
    WOLF("ua.com.javarush.dhashuk.wonderisland.entity.predators.Wolf");

    private String className;

    AnimalsClassName(String name){
    this.className = name;
    }

    public String getClassName(){
        return className;
    }
}
