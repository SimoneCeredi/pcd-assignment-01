package model.pool;

import model.Counter;
import model.FileInfo;

import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

public interface DataManagersPool extends ThreadPool {

    void submitFileInfo(FileInfo fileInfo);

    HashMap<Integer, Counter> getLineCount();

    PriorityBlockingQueue<FileInfo> getTopN();
}
