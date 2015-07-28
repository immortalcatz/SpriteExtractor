package io.github.mribby.junkjack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class Equips extends Spritesheet {
    @Override
    public String getImageName() {
        return "equip";
    }

    @Override
    public String getJsonName() {
        return "treasures";
    }

    @Override
    public String getName() {
        return "equips";
    }

    @Override
    public JSONArray getJsonArray(String string) {
        return new JSONArray(string);
    }

    @Override
    public void saveSprites(BufferedImage image, JSONArray json, File dir) {
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            String name = jsonObject.getString("name");
            String kind = jsonObject.getString("kind");
            int index = jsonObject.optInt("index", -1);

            if (index != -1) {
                if ("feet".equals(kind)) {
                    int column = index % 34;
                    int row = index / 34;
                    int x = column * 18;
                    int y = 310 + row * 18;
                    BufferedImage left = image.getSubimage(x, y, 8, 9);
                    BufferedImage right = image.getSubimage(x + 9, y, 8, 9);

                    BufferedImage sprite = new BufferedImage(15, 9, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D graphics = sprite.createGraphics();
                    graphics.drawImage(right, 7, 0, null);
                    graphics.drawImage(left, 0, 0, null);
                    graphics.dispose();

                    BufferedImage resized = getResizedImage(sprite, 30, 18, 60, 60, 18, 42);
                    saveImage(resized, name, dir);
                }
            }
        }
    }
}
