package model.data;

import model.data.monitor.UnmodifiableCounter;
import utils.Pair;

import java.util.Map;

public interface LineCounter {
    void store(FileInfo fileInfo);

    Map<Pair<Integer, Integer>, UnmodifiableCounter> get();
}
