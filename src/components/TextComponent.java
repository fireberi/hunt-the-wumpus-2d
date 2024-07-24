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
    public FontWeight weight;
    public int size;
    public TextAlignment alignment;
    public Color color;
    public TextLogic textLogic;

    public TextComponent(String text, String font, FontWeight weight, int size, TextAlignment alignment) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.weight = weight;
        this.size = size;
        this.alignment = alignment;
        this.color = Color.WHITE;
        this.textLogic = null;
    }

    public TextComponent(String text, String font, FontWeight weight, int size, TextAlignment alignment, Color color) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.weight = weight;
        this.size = size;
        this.alignment = alignment;
        this.color = color;
        this.textLogic = textLogic;
    }

    public TextComponent(String text, String font, FontWeight weight, int size, TextAlignment alignment, Color color, TextLogic textLogic) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.weight = weight;
        this.size = size;
        this.alignment = alignment;
        this.color = color;
        this.textLogic = textLogic;
    }

}