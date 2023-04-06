package model.pool;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;
import utils.Pair;

import java.util.Collection;
import java.util.Map;

public interface DataManagersPool extends ThreadPool {

    void submitFileInfo(FileInfo fileInfo);

    Map<Pair<Integer, Integer>, UnmodifiableCounter> getLineCounter();

    Collection<FileInfo> getLongestFiles();
}
