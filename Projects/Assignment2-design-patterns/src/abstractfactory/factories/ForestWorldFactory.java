package abstractfactory.factories;

import abstractfactory.components.*;

public class ForestWorldFactory implements WorldFactory {
    @Override
    public Enemy createEnemy() {
        return new ForestEnemy();
    }

    @Override
    public Terrain createTerrain() {
        return new ForestTerrain();
    }

    @Override
    public Soundtrack createSoundtrack() {
        return new ForestSoundtrack();
    }
}