package abstractfactory.components;

public class IceSoundtrack implements Soundtrack {
    @Override
    public void play() {
        System.out.println("Audio track playing: 'Frozen Peaks Echoes.ogg'");
    }
}