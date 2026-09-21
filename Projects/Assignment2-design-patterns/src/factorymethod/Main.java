package factorymethod;

public class Main {
    public static void main(String[] args) {
        // Клиент работает только с абстракцией EnemySpawner
        EnemySpawner spawner;

        spawner = new GoblinSpawner();
        spawner.triggerWaveEvent();

        spawner = new SkeletonSpawner();
        spawner.triggerWaveEvent();

        spawner = new SlimeSpawner();
        spawner.triggerWaveEvent();
    }
}