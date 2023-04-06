package controller;

import model.Model;
import view.View;

public class ControllerImpl implements Controller {
    private final Model model;
    private final View view;
    private volatile boolean shouldStop = false;
    private long startTime;

    public ControllerImpl(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.model.start();
        this.createViewUpdater();
    }

    private void createViewUpdater() {
        new Thread(() -> {
            while (!shouldStop) {
                try {
                    Thread.sleep(50);
                    this.view.update(this.model.getLineCounter(), this.model.getLongestFiles());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Exploration completed in " + (System.currentTimeMillis() - startTime) + "ms");
        }).start();
        this.model.onFinish(this::stop);
    }

    @Override
    public void stop() {
        this.shouldStop = true;
        this.model.stop();
    }
}
