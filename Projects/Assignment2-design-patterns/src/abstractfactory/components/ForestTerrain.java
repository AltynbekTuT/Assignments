package abstractfactory.components;

public class ForestTerrain implements Terrain {
    @Override
    public void render() {
        System.out.println("Terrain: Dense foliage, tall oak trees, damp moss.");
    }
}