package abstractfactory.components;

public class ForestSoundtrack implements Soundtrack {
    @Override
    public void play() {
        System.out.println("Audio track playing: 'Mystic Canopy Ambience.ogg'");
    }
}