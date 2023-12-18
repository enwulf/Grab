package ru.enwulf.grab.ui.text;

import lombok.Getter;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

@Getter
public class CanvasTextGenerator {
    private static final int IMAGE_SIZE = 128;

    private final BufferedImage bufferedImage;
    private final Graphics graphics;

    public CanvasTextGenerator(Color bgColor) {
        bufferedImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.getGraphics();
        graphics.setColor(bgColor);
        graphics.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);
    }


    public void drawText(TextConfig config, int x, int y) {
        Font originalFont = new Font(config.getFontName(), config.fontStyle, config.getFontSize());
        Font font = scaleFontToFit(config.getText(), originalFont);

        graphics.setFont(font);
        graphics.setColor(config.getTextColor());
        graphics.drawString(config.getText(), x, y);
    }

    private Font scaleFontToFit(String text, Font originalFont) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D bounds = originalFont.getStringBounds(text, frc);

        if (bounds.getWidth() > CanvasTextGenerator.IMAGE_SIZE) {
            float scale = (float) (CanvasTextGenerator.IMAGE_SIZE / bounds.getWidth());
            int newSize = (int) (originalFont.getSize() * scale);

            return originalFont.deriveFont((float) newSize);
        } else {
            return originalFont;
        }
    }
}