package abstractfactory;

import abstractfactory.factories.ForestWorldFactory;
import abstractfactory.factories.IceWorldFactory;
import abstractfactory.factories.WorldFactory;

public class Main {
    public static void main(String[] args) {
        // Конфигурация мира меняется ровно в одном месте
        String selectedBiome = "ICE"; // Можно подставить "FOREST"

        WorldFactory factory;
        if ("FOREST".equalsIgnoreCase(selectedBiome)) {
            factory = new ForestWorldFactory();
        } else {
            factory = new IceWorldFactory();
        }

        GameWorldClient client = new GameWorldClient(factory);
        client.loadWorld();
    }
}