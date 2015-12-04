package io.github.mribby.junkjack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class Equips extends Spritesheet {
    private static final int CANVAS_WIDTH = 60;
    private static final int CANVAS_HEIGHT = 60;

    @Override
    protected String getName() {
        return "equips";
    }

    @Override
    protected String getImageName() {
        return "equip";
    }

    @Override
    protected String getJsonName() {
        return "treasures";
    }

    @Override
    protected void saveSprites(BufferedImage image, JSONArray json, File dir) {
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            String name = jsonObject.getString("name");
            Kind kind = Kind.getKind(jsonObject.getString("kind"));
            int index = jsonObject.optInt("index", -1);
            BufferedImage resized = null;

            if (index != -1) {
                if (kind == Kind.hat) {
                    int column = index % 29;
                    int row = index / 29;
                    int x = column * 21;
                    int y = 374 + row * 23;
                    BufferedImage sprite = image.getSubimage(x, y, 21, 23);
                    resized = getResizedImage(sprite, 42, 46, CANVAS_WIDTH, CANVAS_HEIGHT, 12, 2);
                } else if (kind == Kind.chest) {
                    int column = index % 19;
                    int row = index / 19;
                    int x = column * 32;
                    int y = 49 + row * 34;
                    BufferedImage sprite = image.getSubimage(x, y, 22, 15);
                    resized = getResizedImage(sprite, 44, 30, CANVAS_WIDTH, CANVAS_HEIGHT, 12, 32);
                } else if (kind == Kind.legs || kind == Kind.feet) {
                    int column = index % 34;
                    int row = index / 34;
                    int x = column * 18;
                    int y = (kind == Kind.legs ? 301 : 310) + row * 18;
                    BufferedImage left = image.getSubimage(x, y, 8, 9);
                    BufferedImage right = image.getSubimage(x + 9, y, 8, 9);

                    BufferedImage sprite = new BufferedImage(15, 9, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D graphics = sprite.createGraphics();
                    graphics.drawImage(right, 7, 0, null);
                    graphics.drawImage(left, 0, 0, null);
                    graphics.dispose();

                    resized = getResizedImage(sprite, 30, 18, CANVAS_WIDTH, CANVAS_HEIGHT, 18, 42);
                }
            }

            if (resized != null) {
                saveImage(resized, name, dir);
            }
        }
    }
}
