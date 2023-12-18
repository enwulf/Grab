package ru.enwulf.grab.ui.text;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.*;

@Accessors(chain = true)
@Getter @Setter
public class TextConfig {
    private String text;
    private Color textColor;
    private String fontName;
    public int fontStyle;
    private int fontSize;

    public TextConfig(String text) {
        this.text = text;

        if (fontName == null) {
            fontName = "Ubuntu Mono";
        }
        if (fontSize == 0) {
            fontSize = 12;
        }
        if (textColor == null) {
            textColor = Color.WHITE;
        }
    }
}