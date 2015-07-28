package io.github.mribby.junkjack;

import org.json.JSONArray;

import java.awt.image.BufferedImage;
import java.io.File;

public class All extends Spritesheet {
    @Override
    public int saveSprites() {
        int sprites = 0;

        for (Spritesheet spritesheet : SPRITESHEETS) {
            if (spritesheet != this) {
                sprites += spritesheet.saveSprites();
            }
        }

        return sprites;
    }

    @Override
    protected String getName() {
        return "all";
    }

    @Override
    protected JSONArray getJsonArray(String string) {
        return null;
    }

    @Override
    protected void saveSprites(BufferedImage image, JSONArray json, File dir) {}
}
