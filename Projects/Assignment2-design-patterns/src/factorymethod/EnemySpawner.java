package factorymethod;

public abstract class EnemySpawner {
    
    // Factory Method
    public abstract Enemy createEnemy();

    // Бизнес-метод, использующий продукт через интерфейс
    public void triggerWaveEvent() {
        Enemy enemy = createEnemy();
        System.out.println("=== Wave Alert ===");
        System.out.println("A wild " + enemy.getName() + " has entered the arena!");
        enemy.attack();
        System.out.println("==================\n");
    }
}