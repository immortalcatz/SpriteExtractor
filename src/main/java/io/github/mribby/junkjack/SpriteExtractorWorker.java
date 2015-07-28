package io.github.mribby.junkjack;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class SpriteExtractorWorker extends SwingWorker<Void, String> {
    protected final SpriteExtractorFrame frame;
    private final boolean exceptionExit;

    public SpriteExtractorWorker(SpriteExtractorFrame frame, boolean exceptionExit) {
        this.frame = frame;
        this.exceptionExit = exceptionExit;
    }

    @Override
    protected void process(List<String> list) {
        frame.disableGui(list.get(list.size() - 1));
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (ExecutionException e) {
            JOptionPane.showMessageDialog(frame, e.getCause(), "Error", JOptionPane.ERROR_MESSAGE);

            if (exceptionExit) {
                System.exit(1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        frame.resetGui();
    }
}
