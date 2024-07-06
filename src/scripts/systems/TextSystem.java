package scripts.systems;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import dev.dominion.ecs.api.Dominion;

import scripts.components.PositionComponent;
import scripts.components.TextComponent;
import scripts.components.PlayerControllerComponent;
import scripts.components.VelocityComponent;
import scripts.components.BoxColliderComponent;

public class TextSystem implements Runnable {

    private Dominion cherry;
    private GraphicsContext ctx;
    private int tickCounter = 0;

    public TextSystem(Dominion cherry, GraphicsContext ctx) {
        this.cherry = cherry;
        this.ctx = ctx;
    }

    public void run() {
        cherry.findEntitiesWith(PositionComponent.class, TextComponent.class).stream().forEach(e -> {
            PositionComponent pos = e.comp1();
            TextComponent txt = e.comp2();

            boolean right;
            boolean left;
            boolean top;
            boolean bottom;

            cherry.findEntitiesWith(PlayerControllerComponent.class, BoxColliderComponent.class).stream().forEach(player -> {
                BoxColliderComponent boxCol = player.comp2();
                txt.text = "     " + boxCol.top + "\n" + boxCol.left + "     " + boxCol.right + "\n" + "     " + boxCol.bottom;
            });

            ctx.setFill(Color.WHITE);
            ctx.setFont(txt.font);
            ctx.setTextAlign(TextAlignment.LEFT);
            ctx.fillText(txt.text, pos.x, pos.y);
        });
    }
    
}