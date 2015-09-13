package io.github.mribby.junkjack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.File;

public class Blocks extends Spritesheet {
    @Override
    protected String getName() {
        return "blocks";
    }

    @Override
    protected String getImageName() {
        return "rocks";
    }

    @Override
    protected void saveSprites(BufferedImage image, JSONArray json, File dir) {
        BufferedImage leftSheet = image.getSubimage(0, 0, 1024, 2048);
        BufferedImage rightSheet = image.getSubimage(1024, 0, 1024, 2048);

        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObject = json.getJSONObject(i);
            String name = jsonObject.getString("name");
            int id = jsonObject.getInt("id");
            int variants = jsonObject.getInt("variants");

            for (int variant = 0; variant < variants; variant++, id++) {
                String variantName = variants > 1 ? name + " (Variant " + (variant + 1) + ")" : name;
                int index = id < 2048 ? id : id - 2048;
                int x = index % 32 * 32 + 1;
                int y = index / 32 * 32 + 1;
                BufferedImage blockSheet = id < 2048 ? leftSheet : rightSheet;
                BufferedImage sprite = blockSheet.getSubimage(x, y, 30, 30);
                BufferedImage resized = getResizedImage(sprite, 60, 60);
                saveImage(resized, variantName, dir);
            }
        }
    }
}
