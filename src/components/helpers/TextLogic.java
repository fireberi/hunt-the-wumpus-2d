package components.helpers;

import dev.dominion.ecs.api.Dominion;

import components.TextComponent;

public interface TextLogic {
    public void update(Dominion cherry, TextComponent txt);
}