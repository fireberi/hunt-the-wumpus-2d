package scripts.core;

/* import in this order:
- java
- javafx
- scenes
- Dominion
- components
- systems
- core
- util
*/

//region imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
//endregion

public class Main extends Application {

    GameManager gameManager;
    long startTime = System.currentTimeMillis();
    long latestTime = System.currentTimeMillis();
    long runningTime = latestTime - startTime;

    @Override // Application class
    public void start(Stage stage) {
        //region setup canvas
        Canvas canvas = new Canvas();
        canvas.setWidth(Constants.WIDTH * Constants.VIEWPORT_SCALE);
        canvas.setHeight(Constants.HEIGHT * Constants.VIEWPORT_SCALE);
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        ctx.setImageSmoothing(false);
        //endregion

        //region text displays
        Text textFPS = new Text();
        textFPS.setText("FPS: ");
        textFPS.setFill(Color.WHITE);
        textFPS.setFont(Font.font("PT Mono", FontWeight.BOLD, 12));
        textFPS.setTextAlignment(TextAlignment.LEFT);
        textFPS.setTextOrigin(VPos.CENTER);
        textFPS.setX(2);
        textFPS.setY(8);

        Text textTick = new Text();
        textTick.setText("Tick: ");
        textTick.setFill(Color.WHITE);
        textTick.setFont(Font.font("PT Mono", FontWeight.BOLD, 12));
        textTick.setTextAlignment(TextAlignment.LEFT);
        textTick.setTextOrigin(VPos.CENTER);
        textTick.setX(2);
        textTick.setY(24);
        //endregion

        //region setup javafx stage and scene
        Group root = new Group();

        root.getChildren().add(canvas);
        root.getChildren().add(textFPS);
        root.getChildren().add(textTick);

        Scene scene = new Scene(root, Constants.WIDTH * Constants.VIEWPORT_SCALE, Constants.HEIGHT * Constants.VIEWPORT_SCALE, Color.BLACK);

        stage.setTitle("Hunt the Wumpus");
        stage.setScene(scene);
        stage.show();
        stage.setY(0);
        //endregion

        //region setup managers
        gameManager = new GameManager();
        gameManager.init();

        scene.setOnKeyPressed((KeyEvent e) -> InputManager.handleKeyPressed(e));
        scene.setOnKeyReleased((KeyEvent e) -> InputManager.handleKeyReleased(e));
        // scene.setOnMouseClicked((MouseEvent e) -> InputManager.handleMouseClicked(e));
        //endregion

        //region game loop
        new AnimationTimer() {
            private long last = 0;
            private double accumulator = 0;
            private double TPS = 120;
            private double lowerTickInterval = 1000000000.0 / (TPS - 1);
            private double upperTickInterval = 1000000000.0 / (TPS + 1);
            private double fuzzyTickInterval = 1000000000.0 / (TPS + 2);
            private double tickInterval = 1000000000.0 / TPS;
            private long cappedDeltaLimit = (long) 1000000000.0 / 20;
            // private long cappedDeltaLimit = (long) tickInterval;

            private final int filterStrength = 4;
            private double filteredFrameTime = 0;
            private int updateFPSCounter = 0;
            private final int maxUpdateFPSCount = 5;

            private int tick = 0;

            private boolean freezed = false;

            @Override
            public void handle(long now) {
                /*
                NOTE: delta, now, and last are in nanoseconds
                Math.min is used to cap delta time at 20 FPS (otherwise the
                game will have have significant startup lag)
                */
                latestTime = System.currentTimeMillis();

                long delta = now - last;
                long cappedDelta = Math.min(now - last, cappedDeltaLimit);
                last = now;


                if (InputManager.inputs.get("_freeze").justPressed()) {
                    freezed = !freezed;
                }

                if (!freezed) {
                    accumulator += cappedDelta;

                    while (accumulator >= fuzzyTickInterval) {
                        // will pass in seconds
                        gameManager.update(tickInterval / 1000000000.0, ctx);

                        accumulator -= tickInterval;
                        if (accumulator < 0) {
                            accumulator = 0;
                        }

                        InputManager.cleanUpInputs();

                        tick += 1;
                    }
                }
                else {
                    if (InputManager.inputs.get("_advance").justPressed()) {
                        gameManager.update(tickInterval / 1000000000.0, ctx);

                        accumulator = 0;

                        InputManager.cleanUpInputs();

                        tick += 1;
                    }
                }

                gameManager.render(ctx, stage);
                InputManager.cleanUpDevInputs();

                // FPS
                filteredFrameTime += (delta - filteredFrameTime) / filterStrength;
                updateFPSCounter++;
                if (updateFPSCounter == maxUpdateFPSCount) {
                    updateFPSCounter = 0;
                    textFPS.setText("FPS: " + (int) Math.floor(1000000000 / filteredFrameTime));
                }

                // tick
                textTick.setText("Tick: " + tick);
            }
        }.start();
        //endregion
    }

    @Override
    public void stop() {
        System.out.println("stopping");

        gameManager.shutDown();
    }

    public static void main(String[] args) {
        launch(args);
    }

}