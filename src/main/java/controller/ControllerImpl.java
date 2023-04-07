package controller;

import model.Model;

import java.io.File;

public class ControllerImpl implements Controller {
    private final Model model;

    public ControllerImpl(Model model) {
        this.model = model;
    }

    @Override
    public void start() {
        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            this.model.start();
            this.model.onFinish(() -> {
                this.stop();
                System.out.println("Directory exploration completed in " + (System.currentTimeMillis() - startTime) + "ms");
            });
        }).start();

    }

    @Override
    public void stop() {
        new Thread(this.model::stop).start();
    }

    @Override
    public void changeParams(File d, int ni, int maxl, int n) {
        new Thread(() -> this.model.changeParams(d, ni, maxl, n)).start();
    }
}
