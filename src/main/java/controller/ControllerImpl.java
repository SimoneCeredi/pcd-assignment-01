package controller;

import model.Model;

public class ControllerImpl implements Controller {
    private final Model model;

    public ControllerImpl(Model model) {
        this.model = model;
    }

    @Override
    public void start() {
        long startTime = System.currentTimeMillis();
        this.model.start();
        this.model.onFinish(() -> {
            this.stop();
            System.out.println("Directory exploration completed in " + (System.currentTimeMillis() - startTime) + "ms");
        });

    }

    @Override
    public void stop() {
        this.model.stop();
    }
}
