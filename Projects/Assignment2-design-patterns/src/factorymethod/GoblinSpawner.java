package factorymethod;

public class GoblinSpawner extends EnemySpawner {
    @Override
    public Enemy createEnemy() {
        return new Goblin();
    }
}