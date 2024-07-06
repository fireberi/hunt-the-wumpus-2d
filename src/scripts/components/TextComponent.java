package scripts.components;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TextComponent {

    public String text;
    public Font font;

    public TextComponent(String text, String font, FontWeight weight, int size) {
        this.text = text;
        this.font = Font.font(font, weight, size);
    }

}