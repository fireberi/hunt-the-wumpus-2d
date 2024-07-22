package systems;

import dev.dominion.ecs.api.Dominion;

import components.SpriteComponent;

import core.State;
import util.Frame;

public class SpriteSystem implements Runnable {

    private Dominion cherry;
    private State state;

    public SpriteSystem(Dominion cherry, State state) {
        this.cherry = cherry;
        this.state = state;
    }

    public void run() {
        cherry.findEntitiesWith(SpriteComponent.class).stream().forEach(e -> {
            SpriteComponent spr = e.comp();

            if (!spr.active) {
                return;
            }

            // switch and reset animation
            if (spr.currentAnim != spr.nextAnim) {
                spr.frame().time = 0;
                spr.currentAnim = spr.nextAnim;
                spr.currentFrame = 0;
            }

            Frame frame = spr.frame();
            double waitTime = spr.waitTime();

            frame.time += state.delta;

            if (frame.time >= waitTime) {
                double overlap = frame.time - waitTime;
                frame.time = 0;

                boolean test = true;
                if (test) {
                    if (spr.repeat()) {
                        if (spr.currentFrame + 1 >= spr.frames().length) {
                            spr.currentFrame = 0;
                        }
                        else {
                            spr.currentFrame += 1;
                        }
                    }
                    else {
                        if (spr.currentFrame + 1 < spr.frames().length) {
                            spr.currentFrame += 1;
                        }
                    }
                }
                else {
                    spr.currentFrame += 1;

                    System.out.println("hmm");
                    System.out.println("currentAnim: " + spr.currentAnim);
                    System.out.println("currentFrame: " + spr.currentFrame);
                    System.out.println("repeat: " + spr.repeat());

                    if (spr.currentFrame >= spr.frames().length && spr.repeat()) {
                        spr.currentFrame = 0;
                        System.out.println("yay");
                        System.out.println("currentAnim: " + spr.currentAnim);
                        System.out.println("currentFrame: " + spr.currentFrame);
                        System.out.println("repeat: " + spr.repeat());
                    }
                    else if (spr.currentFrame >= spr.frames().length && !spr.repeat()) {
                        System.out.println("whoops");
                        System.out.println("currentAnim: " + spr.currentAnim);
                        System.out.println("currentFrame: " + spr.currentFrame);
                        System.out.println("repeat: " + spr.repeat());
                    }
                }

                // account for overlap in time, i.e. when frame.time - waitTime > 0
                spr.frame().time = overlap;
            }
        });
    }

}