package io.github.mribby.junkjack;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Spritesheet {
    private int sprites;

    public int saveSprites() {
        BufferedImage image;
        JSONArray json;

        try {
            image = ImageIO.read(new File(getImageName() + ".png"));
            json = getJsonArray(IOUtils.toString(new FileInputStream(getJsonName() + ".json")));
        } catch (IOException e) {
            throw new RuntimeException("Could not load spritesheet: " + getName(), e);
        }

        File dir = Downloader.createDir(new File(getDirName()));
        sprites = 0;
        saveSprites(image, json, dir);
        return sprites;
    }

    protected abstract String getName();

    protected String getDirName() {
        return getName();
    }

    protected String getImageName() {
        return getName();
    }

    protected String getJsonName() {
        return getName();
    }

    protected JSONArray getJsonArray(String string) {
        return new JSONArray(string);
    }

    protected abstract void saveSprites(BufferedImage image, JSONArray json, File dir);

    protected final BufferedImage getResizedImage(Image image, int width, int height) {
        return getResizedImage(image, width, height, width, height, 0, 0);
    }

    protected final BufferedImage getResizedImage(Image image, int width, int height, int canvasWidth, int canvasHeight, int x, int y) {
        BufferedImage resized = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = resized.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics.drawImage(image, x, y, width, height, null);
        graphics.dispose();
        return resized;
    }

    protected final void saveImage(RenderedImage image, String spriteName, File dir) {
        try {
            ImageIO.write(image, "png", new File(dir, spriteName + ".png"));
            sprites++;
        } catch (IOException e) {
            System.err.println("Could not save image: " + spriteName);
            e.printStackTrace();
        }
    }
}
