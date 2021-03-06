package io.github.mribby.junkjack;

public class SpriteExtractor {
    public static void main(String[] args) {
        if (args.length > 0) {
            Downloader.downloadDependencies();

            for (String arg : args) {
                extractSprites(arg);
            }
        } else {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    SpriteExtractorFrame.createGui();
                }
            });
        }
    }

    public static void extractSprites(String name) {
        Spritesheet spritesheet = Spritesheet.getSpritesheet(name);

        if (spritesheet != null) {
            System.out.println("Extracting sprites: " + spritesheet.getName());
            long start = System.currentTimeMillis();
            int sprites = spritesheet.saveSprites();
            long duration = System.currentTimeMillis() - start;
            System.out.println("Extracted " + sprites + " sprites in " + duration + " ms");
        } else {
            throw new RuntimeException("Invalid spritesheet: " + name);
        }
    }
}
