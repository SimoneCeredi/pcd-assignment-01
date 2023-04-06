package model.pool;

import model.data.FileInfo;
import model.data.LineCounter;
import model.data.monitor.LongestFilesQueue;

public interface DataManagersPool extends ThreadPool {

    void submitFileInfo(FileInfo fileInfo);

    LineCounter getLineCount();

    LongestFilesQueue getTopN();
}
