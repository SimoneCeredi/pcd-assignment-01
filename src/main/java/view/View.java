package view;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;
import utils.Pair;

import java.util.Collection;
import java.util.Map;

public interface View {
    void update(Map<Pair<Integer, Integer>, UnmodifiableCounter> lineCounter, Collection<FileInfo> longestFiles);
}
