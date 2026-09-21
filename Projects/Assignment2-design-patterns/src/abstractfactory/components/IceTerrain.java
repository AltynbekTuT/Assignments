package abstractfactory.components;

public class IceTerrain implements Terrain {
    @Override
    public void render() {
        System.out.println("Terrain: Slippery glaciers, frozen lakes, falling snow.");
    }
}