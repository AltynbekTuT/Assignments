package abstractfactory.components;

public class ForestEnemy implements Enemy {
    @Override
    public void spawn() {
        System.out.println("Encountered: Forest Spider crawling from canopy.");
    }
}