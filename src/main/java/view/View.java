package view;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;

import java.util.Collection;
import java.util.Map;

public interface View {
    void update(Map<Integer, UnmodifiableCounter> lineCounter, Collection<FileInfo> longestFiles);
}
