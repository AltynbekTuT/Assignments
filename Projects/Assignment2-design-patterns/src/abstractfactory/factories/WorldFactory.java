package abstractfactory.factories;

import abstractfactory.components.Enemy;
import abstractfactory.components.Soundtrack;
import abstractfactory.components.Terrain;

public interface WorldFactory {
    Enemy createEnemy();
    Terrain createTerrain();
    Soundtrack createSoundtrack();
}