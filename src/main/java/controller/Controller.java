package controller;

import java.io.File;

public interface Controller {
    void start();

    void stop();

    void changeParams(File d, int ni, int maxl, int n);
}
