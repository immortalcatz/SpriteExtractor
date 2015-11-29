package io.github.mribby.junkjack;

public class SpriteExtractor {
    public static void main(String[] args) {
        Downloader.downloadDependencies();
        extractSprites();
    }

    public static void extractSprites() {
        Spritesheet spritesheet = new Items();
        System.out.println("Extracting sprites: " + spritesheet.getName());
        long start = System.currentTimeMillis();
        int sprites = spritesheet.saveSprites();
        long duration = System.currentTimeMillis() - start;
        System.out.println("Extracted " + sprites + " sprites in " + duration + " ms");
    }
}
