package io.github.mribby.junkjack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Items extends Spritesheet {
    @Override
    protected String getName() {
        return "items";
    }

    @Override
    protected String getImageName() {
        return "treasures";
    }

    @Override
    protected String getJsonName() {
        return "english";
    }

    @Override
    protected JSONArray getJsonArray(String string) {
        return new JSONObject(string).getJSONArray("treasures");
    }

    @Override
    protected void saveSprites(BufferedImage image, JSONArray json, File dir) {
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            int x = id / 64 * 16;
            int y = id % 64 * 16;
            BufferedImage sprite = getCroppedImage(image.getSubimage(x, y, 16, 16));
            int w = sprite.getWidth();
            int h = sprite.getHeight();
            int resizedW = w * 2;
            int resizedH = h * 2;
            int spriteX = 16 - w;
            int spriteY = 16 - h;
            BufferedImage resized = getResizedImage(sprite, resizedW, resizedH, 32, 32, spriteX, spriteY);
            saveImage(resized, name, dir);
        }
    }

    private BufferedImage getCroppedImage(BufferedImage img) {
        // http://stackoverflow.com/a/7385833

        int height        = img.getHeight();
        int width         = img.getWidth();
        int trimmedWidth  = 0;
        int trimmedHeight = 0;

        for(int i = 0; i < height; i++) {
            for(int j = width - 1; j >= 0; j--) {
                Color color = new Color(img.getRGB(j, i), true);
                if(color.getAlpha() != 0 && j > trimmedWidth) {
                    trimmedWidth = j;
                    break;
                }
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = height - 1; j >= 0; j--) {
                Color color = new Color(img.getRGB(i, j), true);
                if(color.getAlpha() != 0 && j > trimmedHeight) {
                    trimmedHeight = j;
                    break;
                }
            }
        }

        return img.getSubimage(0, 0, trimmedWidth + 1, trimmedHeight + 1);
    }
}
