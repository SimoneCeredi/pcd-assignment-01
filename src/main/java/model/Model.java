package model;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;
import observers.ModelObserver;
import utils.Pair;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public interface Model {
    Map<Pair<Integer, Integer>, UnmodifiableCounter> getLineCounter();

    Collection<FileInfo> getLongestFiles();

    void start();

    void stop();

    void onFinish(Runnable callback);

    void addObserver(ModelObserver observer);

    void changeParams(File d, int ni, int maxl, int n);
}
