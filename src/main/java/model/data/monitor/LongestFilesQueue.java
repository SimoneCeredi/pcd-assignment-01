package model.data.monitor;

import model.data.FileInfo;

public interface LongestFilesQueue {
    void put(FileInfo fileInfo);
}
