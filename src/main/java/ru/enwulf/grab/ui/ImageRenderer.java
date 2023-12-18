package ru.enwulf.grab.ui;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import ru.enwulf.grab.Grab;
import ru.enwulf.grab.ui.text.CanvasTextGenerator;
import ru.enwulf.grab.ui.text.TextConfig;
import ru.enwulf.grab.utils.Config;

import java.awt.*;


public class ImageRenderer extends MapRenderer {

    private boolean done = false;
    private int yOffset = 40;

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if (done)
            return;

        mapView.setTrackingPosition(false);

     /*   BufferedImage image = loadImage(player);

        if (image == null) {
            MapProvider.removeMapFromPlayer(player);
            throw new NullPointerException("Image is null");
        }*/

        CanvasTextGenerator textGenerator = new CanvasTextGenerator(Color.BLACK);

        Config config = Grab.get().getCfg();

        String title = config.getMenuTitle();
        Graphics graphics = textGenerator.getGraphics();
        textGenerator.drawText(
                new TextConfig(title)
                        .setFontName(config.getFontName())
                        .setTextColor(Color.YELLOW)
                        .setFontStyle(Font.BOLD)
                        .setFontSize(12),
                (int) ((128 - textGenerator.getGraphics().getFontMetrics().getStringBounds(title, graphics).getWidth()) / 2),
                15
        );

        for (String s : config.getTexts()) {
            textGenerator.drawText(new TextConfig(s)
                            .setFontName(config.getFontName())
                            .setTextColor(Color.WHITE)
                            .setFontStyle(Font.PLAIN)
                            .setFontSize(12),
                    5, yOffset);

            yOffset = yOffset + 15;
        }

        mapCanvas.drawImage(0, 0, MapPalette.resizeImage(textGenerator.getBufferedImage()));

        done = true;
    }


}

 /*   public BufferedImage loadImage(Player player) {
        try {
            if (!imageFile.exists()) {
                player.sendMessage("§cImage not found at the path: \"" + imageFile.getPath() + "\"");
                return null;
            }
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            player.sendMessage("§cFailed to read the image.");
            return null;
        }
    }
}*/