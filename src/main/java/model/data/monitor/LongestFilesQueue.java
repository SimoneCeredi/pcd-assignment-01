package model.data.monitor;

import model.data.FileInfo;

import java.util.Collection;

public interface LongestFilesQueue {
    void put(FileInfo fileInfo);

    Collection<FileInfo> get();
}
