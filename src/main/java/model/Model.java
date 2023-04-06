package model;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public interface Model {
    Map<Integer, UnmodifiableCounter> getLineCounter();

    Collection<FileInfo> getLongestFiles();

    void start();

    void stop();

    void onFinish(Runnable callback);

    void changeDir(File newDir);
}
