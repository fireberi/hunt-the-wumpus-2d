package scripts.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import scripts.components.ImageComponent;
import scripts.util.Frame;

public class SpriteComponent {

    public ImageComponent image;
    public String[] names;
    public boolean[] repeats;
    public double[] waitTimes;
    public Frame[][] frameData;
    public String currentAnim = "";
    public String nextAnim;
    public int currentFrame = 0;
    public boolean active;
    public int layer;
    // this property is for the render system to know whether this entity has been cached
    public boolean hasCached = false;

    public SpriteComponent(ImageComponent image) {
        this.image = image;

        this.names = new String[] {"default"};
        this.waitTimes = new double[] {0};
        this.frameData = new Frame[][] {{new Frame(0, 0, image.w, image.h, 0, 0)}};
        this.nextAnim = "default";
        this.active = false;
        this.layer = 0;
        this.repeats = new boolean[] {false};
    }

    public SpriteComponent(ImageComponent image, String nextAnim, boolean active, String[] names, boolean[] repeats, double[] waitTimes, Frame[][] frames) {
        this.image = image;

        this.names = names;
        this.waitTimes = waitTimes;
        this.frameData = frames;
        this.nextAnim = nextAnim;
        this.active = active;
        this.layer = 0;
        this.repeats = repeats;
    }

    public SpriteComponent(ImageComponent image, int layer) {
        this.image = image;

        this.names = new String[] {"default"};
        this.waitTimes = new double[] {0};
        this.frameData = new Frame[][] {{new Frame(0, 0, image.w, image.h, 0, 0)}};
        this.nextAnim = "default";
        this.active = false;
        this.layer = layer;
        this.repeats = new boolean[] {false};
    }

    public SpriteComponent(ImageComponent image, int layer, String nextAnim, boolean active, String[] names, boolean[] repeats, double[] waitTimes, Frame[][] frames) {
        this.image = image;

        this.names = names;
        this.waitTimes = waitTimes;
        this.frameData = frames;
        this.nextAnim = nextAnim;
        this.active = active;
        this.layer = layer;
        this.repeats = repeats;
    }

    public Frame frame() {
        return frameData[index(names, currentAnim)][currentFrame];
    }

    public double waitTime() {
        return waitTimes[index(names, currentAnim)];
    }

    public Frame[] frames() {
        return frameData[index(names, currentAnim)];
    }

    public boolean repeat() {
        return repeats[index(names, currentAnim)];
    }

    private static <T> int index(T[] array, T target) {
        return IntStream.range(0, array.length).filter(i -> target == array[i]).findFirst().orElse(-1);
    }

}