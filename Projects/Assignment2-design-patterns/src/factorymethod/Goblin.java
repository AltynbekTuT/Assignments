package factorymethod;

public class Goblin implements Enemy {
    @Override
    public void attack() {
        System.out.println("Goblin slashes with a rusty dagger for 12 damage!");
    }

    @Override
    public String getName() {
        return "Cave Goblin";
    }
}