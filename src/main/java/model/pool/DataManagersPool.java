package model.pool;

import model.data.FileInfo;
import model.data.monitor.Counter;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

public interface DataManagersPool extends ThreadPool {

    void submitFileInfo(FileInfo fileInfo);

    HashMap<Integer, Counter> getLineCount();

    PriorityBlockingQueue<FileInfo> getTopN();
}
