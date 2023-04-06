package model.data;

import model.data.monitor.UnmodifiableCounter;

import java.util.Map;

public interface LineCounter {
    void store(FileInfo fileInfo);

    Map<Integer, UnmodifiableCounter> get();
}
