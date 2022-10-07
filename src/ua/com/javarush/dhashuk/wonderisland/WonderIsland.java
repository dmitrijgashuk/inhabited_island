package ua.com.javarush.dhashuk.wonderisland;

import ua.com.javarush.dhashuk.wonderisland.handler.statistic.StatisticHandler;
import ua.com.javarush.dhashuk.wonderisland.handler.configurator.Configurator;
import ua.com.javarush.dhashuk.wonderisland.log.WonderLog;
import ua.com.javarush.dhashuk.wonderisland.model.Island;


public class WonderIsland {
    public static final WonderLog LOG = WonderLog.getInstance();

    public static String CREATE_ISLAND_MESSAGE = "Someone created the new island/n " +
            "and called it 'Inhabited Island'/n " +
            " and created each each creature in pairs";

    public static void main(String[] args) {

        Configurator config = Configurator.getConfig();
        config.readProperties("parameter.json");

        Island inhabitedIsland = new Island();
        System.out.println(CREATE_ISLAND_MESSAGE);
        StatisticHandler statisticHandler = new StatisticHandler(inhabitedIsland.getGameZones());
        statisticHandler.showAnimalAmount();

        DayProcessor dayProcessor = new DayProcessor(inhabitedIsland);
        for (int i = 1; i <7; i++) {
            System.out.println("Day: "+ i);
            System.out.println("----------------------------");
            statisticHandler.showAnimalAmount();
            dayProcessor.startNewDay();
            //dayProcessor.concurrentNewDay();
        }
    }

}
