package model.pool;

import model.UpdatableModel;
import model.data.FileInfo;
import model.data.LineCounter;
import model.data.LineCounterImpl;
import model.data.monitor.LongestFilesQueue;
import model.data.monitor.LongestFilesQueueImpl;
import model.data.monitor.UnmodifiableCounter;
import model.task.datamanager.DataManagerTaskImpl;
import utils.Pair;

import java.util.Collection;
import java.util.Map;

public class DataManagersPoolImpl extends ThreadPoolImpl implements DataManagersPool {

    private final LineCounter lineCounter;
    private final LongestFilesQueue longestFiles;
    private final UpdatableModel model;

    public DataManagersPoolImpl(UpdatableModel model, int numThreads, String name, int ni, int maxl, int n) {
        super(numThreads, name);
        this.longestFiles = new LongestFilesQueueImpl(n);
        this.lineCounter = new LineCounterImpl(ni, maxl);
        this.model = model;
    }

    @Override
    public void submitFileInfo(FileInfo fileInfo) {
        super.submitTask(new DataManagerTaskImpl(this.model, this.lineCounter, this.longestFiles, fileInfo));
    }

    @Override
    public Map<Pair<Integer, Integer>, UnmodifiableCounter> getLineCounter() {
        return this.lineCounter.get();
    }

    @Override
    public Collection<FileInfo> getLongestFiles() {
        return this.longestFiles.get();
    }
}
