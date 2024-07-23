package scenes;

import javafx.scene.canvas.GraphicsContext;

import dev.dominion.ecs.api.Dominion;
import dev.dominion.ecs.api.Scheduler;

import core.Scene;
import core.State;
import core.Constants;

public class SceneCherry extends Scene {

    public Dominion cherry = Dominion.create("cherry");
    public State state = new State();

    public Scheduler updateScheduler = cherry.createScheduler();
    public Scheduler renderScheduler = cherry.createScheduler();

    @Override
    public void update(double tickInterval) {
        // tickInterval is in seconds
        state.updateDelta(tickInterval);
        if (updateScheduler != null) {
            updateScheduler.tick();
        }
    }

    @Override
    public void render(GraphicsContext ctx) {
        if (renderScheduler != null) {
            renderScheduler.tick();
        }
    }

    @Override
    public void shutDown() {
        updateScheduler.shutDown();
        renderScheduler.shutDown();
        cherry = null;
        updateScheduler = null;
        renderScheduler = null;
        cherry = Dominion.create("cherry");
        updateScheduler = cherry.createScheduler();
        renderScheduler = cherry.createScheduler();
        System.out.println("scene shut down");
    }

}