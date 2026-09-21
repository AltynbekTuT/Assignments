package abstractfactory.components;

public class IceEnemy implements Enemy {
    @Override
    public void spawn() {
        System.out.println("Encountered: Frost Yeti emerging from blizzard.");
    }
}