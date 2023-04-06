package model.pool;

import model.data.FileInfo;
import model.data.LineCounter;
import model.data.LineCounterImpl;
import model.data.monitor.LongestFilesQueue;
import model.data.monitor.LongestFilesQueueImpl;
import model.task.datamanager.DataManagerTaskImpl;

public class DataManagersPoolImpl extends ThreadPoolImpl implements DataManagersPool {

    private final LineCounter lineCounter;
    private final LongestFilesQueue longestFiles;

    public DataManagersPoolImpl(int numThreads, String name, int ni, int maxl, int n) {
        super(numThreads, name);
        this.longestFiles = new LongestFilesQueueImpl(n);
        this.lineCounter = new LineCounterImpl(ni, maxl);
    }

    @Override
    public void submitFileInfo(FileInfo fileInfo) {
        super.submitTask(new DataManagerTaskImpl(this.lineCounter, this.longestFiles, fileInfo));
    }

    @Override
    public LineCounter getLineCount() {
        return this.lineCounter;
    }

    @Override
    public LongestFilesQueue getTopN() {
        return this.longestFiles;
    }
}
