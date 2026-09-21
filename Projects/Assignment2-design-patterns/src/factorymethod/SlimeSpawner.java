package factorymethod;

public class SlimeSpawner extends EnemySpawner {
    @Override
    public Enemy createEnemy() {
        return new Slime();
    }
}