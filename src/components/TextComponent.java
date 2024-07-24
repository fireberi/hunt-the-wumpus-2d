package components;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

import components.helpers.TextLogic;

public class TextComponent {

    public String text;
    public Font font;
    public int size;
    public TextAlignment alignment;
    public Color colour;
    public TextLogic textLogic;

    public TextComponent(String text, String font, FontWeight weight, int size, TextAlignment alignment) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.size = size;
        this.alignment = alignment;
        this.colour = Color.WHITE;
        this.textLogic = null;
    }

    public TextComponent(String text, String font, FontWeight weight, int size, TextAlignment alignment, Color colour) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.alignment = alignment;
        this.size = size;
        this.colour = colour;
        this.textLogic = textLogic;
    }

    public TextComponent(String text, String font, FontWeight weight, int size, TextAlignment alignment, Color colour, TextLogic textLogic) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.alignment = alignment;
        this.size = size;
        this.colour = colour;
        this.textLogic = textLogic;
    }

}