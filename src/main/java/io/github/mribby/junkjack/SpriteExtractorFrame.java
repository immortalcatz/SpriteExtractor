package io.github.mribby.junkjack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpriteExtractorFrame extends JFrame implements ActionListener {
    public static void createGui() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SpriteExtractorFrame frame = new SpriteExtractorFrame();
        frame.setSize(WIDTH, 48 + PADDING + BUTTON_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.downloadDependencies();
    }

    private static final int WIDTH = 300;
    private static final int PADDING = 6;// 24 / 4
    private static final int BUTTON_HEIGHT = 24;

    private final JComboBox comboBox;
    private final JButton button;

    private SpriteExtractorFrame() {
        super("Sprite Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        comboBox = new JComboBox(Spritesheet.NAMES.toArray(new String[0]));
        comboBox.setSize(WIDTH, 24);
        comboBox.setLocation(0, 0);
        button = new JButton();
        button.setSize(WIDTH, BUTTON_HEIGHT);
        button.setLocation(0, 24 + PADDING);
        add(comboBox);
        add(button);
        button.addActionListener(this);
    }

    public void downloadDependencies() {
        new SpriteExtractorWorker(this, true) {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Downloading dependencies...");
                Downloader.downloadDependencies();
                return null;
            }
        }.execute();
    }

    public void actionPerformed(ActionEvent event) {
        new SpriteExtractorWorker(this, false) {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Extracting sprites...");
                SpriteExtractor.extractSprites((String) comboBox.getSelectedItem());
                return null;
            }
        }.execute();
    }

    public void resetGui() {
        updateGui("Extract sprites", true);
    }

    public void disableGui(String text) {
        updateGui(text, false);
    }

    private void updateGui(String text, boolean enabled) {
        button.setText(text);
        button.setEnabled(enabled);
        comboBox.setEnabled(enabled);
    }
}
