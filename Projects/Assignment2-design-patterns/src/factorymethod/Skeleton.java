public class Skeleton implements Enemy {
    @Override
    public void attack() {
        System.out.println("Skeleton shoots a bone arrow from the shadows for 18 damage!");
    }

    @Override
    public String getName() {
        return "Skeleton Archer";
    }
}