package components;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import components.helpers.TextLogic;

public class TextComponent {

    public String text;
    public Font font;
    public TextAlignment alignment;
    public int size;
    public TextLogic textLogic;

    public TextComponent(String text, String font, FontWeight weight, int size) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.alignment = TextAlignment.CENTER;
        this.size = size;
        this.textLogic = null;
    }

    public TextComponent(String text, String font, FontWeight weight, TextAlignment alignment, int size) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.alignment = alignment;
        this.size = size;
        this.textLogic = null;
    }

    public TextComponent(String text, String font, FontWeight weight, int size, TextLogic textLogic) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.alignment = TextAlignment.CENTER;
        this.size = size;
        this.textLogic = textLogic;
    }

    public TextComponent(String text, String font, FontWeight weight, TextAlignment alignment, int size, TextLogic textLogic) {
        this.text = text;
        this.font = Font.font(font, weight, size);
        this.alignment = alignment;
        this.size = size;
        this.textLogic = textLogic;
    }

}