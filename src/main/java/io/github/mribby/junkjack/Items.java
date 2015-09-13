package io.github.mribby.junkjack;

import org.json.JSONArray;
import org.json.JSONObject;

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
        return "treasures";
    }

    @Override
    protected void saveSprites(BufferedImage image, JSONArray json, File dir) {
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            String name = jsonObject.getString("name");
            int variants = jsonObject.getInt("variants");
            JSONArray sizes = jsonObject.getJSONArray("sizes");
            int x = jsonObject.getInt("row") * 16;
            int y = jsonObject.getInt("col") * 16;

            for (int variant = 0; variant < variants; variant++, x += 16) {
                String variantName = variants > 1 ? name + " (Variant " + (variant + 1) + ")" : name;
                int sizeIndex = variant * 2;
                int w = sizes.getInt(sizeIndex);
                int h = sizes.getInt(sizeIndex + 1);
                int resizedW = w * 2;
                int resizedH = h * 2;
                int spriteX = 16 - w;
                int spriteY = 16 - h;
                BufferedImage sprite = image.getSubimage(x, y, w, h);
                BufferedImage resized = getResizedImage(sprite, resizedW, resizedH, 32, 32, spriteX, spriteY);
                saveImage(resized, variantName, dir);
            }
        }
    }
}
