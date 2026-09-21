package factorymethod;

public class Slime implements Enemy {
    @Override
    public void attack() {
        System.out.println("Slime splashes corrosive acid dealing 8 damage!");
    }

    @Override
    public String getName() {
        return "Toxic Slime";
    }
}