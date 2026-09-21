package abstractfactory;

import abstractfactory.components.Enemy;
import abstractfactory.components.Soundtrack;
import abstractfactory.components.Terrain;
import abstractfactory.factories.WorldFactory;

public class GameWorldClient {
    private final Enemy enemy;
    private final Terrain terrain;
    private final Soundtrack soundtrack;

    // Внедрение фабрики через конструктор (композиция)
    public GameWorldClient(WorldFactory factory) {
        this.enemy = factory.createEnemy();
        this.terrain = factory.createTerrain();
        this.soundtrack = factory.createSoundtrack();
    }

    public void loadWorld() {
        System.out.println("Loading level components...");
        terrain.render();
        soundtrack.play();
        enemy.spawn();
        System.out.println("Level loaded successfully.\n");
    }
}