package io.github.mribby.junkjack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

public class Mobs extends Spritesheet {
    @Override
    protected String getName() {
        return "mobs";
    }

    @Override
    protected JSONArray getJsonArray(String string) {
        return new JSONObject(string).getJSONArray("mobs");
    }

    @Override
    protected void saveSprites(BufferedImage image, JSONArray json, File dir) {
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            String name = jsonObject.getString("name");
            JSONArray standingFrame = jsonObject.getJSONArray("standingFrame");
            int x = standingFrame.getInt(0);
            int y = standingFrame.getInt(1);
            int w = standingFrame.getInt(2);
            int h = standingFrame.getInt(3);
            int resizedW = w * 2;
            int resizedH = h * 2;
            int spriteX = 30 - w;
            int spriteY = 60 - resizedH;
            BufferedImage sprite = image.getSubimage(x, y, w, h);
            BufferedImage resized = getResizedImage(sprite, resizedW, resizedH, 60, 60, spriteX, spriteY);
            saveImage(resized, name, dir);
        }
    }
}
