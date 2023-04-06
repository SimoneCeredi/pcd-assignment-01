package model.pool;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;

import java.util.Collection;
import java.util.Map;

public interface DataManagersPool extends ThreadPool {

    void submitFileInfo(FileInfo fileInfo);

    Map<Integer, UnmodifiableCounter> getLineCounter();

    Collection<FileInfo> getLongestFiles();
}
