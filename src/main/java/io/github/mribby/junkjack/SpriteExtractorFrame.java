package io.github.mribby.junkjack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpriteExtractorFrame extends JFrame implements ActionListener {
    public static void createGui() {
        SpriteExtractorFrame frame = new SpriteExtractorFrame();
        frame.setSize(256, 96);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SwingUtilities.updateComponentTreeUI(frame);
        }

        frame.setVisible(true);
        frame.downloadDependencies();
    }

    private final JComboBox comboBox;
    private final JButton button;

    private SpriteExtractorFrame() {
        super("Sprite Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1, 0, 16));
        comboBox = new JComboBox(Spritesheet.NAMES.toArray());
        button = new JButton();
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

    public void updateGui(String text, boolean enabled) {
        button.setText(text);
        button.setEnabled(enabled);
        comboBox.setEnabled(enabled);
    }
}
