package model.pool;

import model.Counter;
import model.CounterImpl;
import model.FileInfo;
import model.task.datamanager.DataManagerTaskImpl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class DataManagersPoolImpl extends ThreadPoolImpl implements DataManagersPool {

    private final HashMap<Integer, Counter> lineCount;
    private final PriorityBlockingQueue<FileInfo> topN;
    private final int n;

    public DataManagersPoolImpl(int numThreads, String name, int ni, int maxl, int n) {
        super(numThreads, name);
        this.topN = new PriorityBlockingQueue<>(n, Comparator.comparingInt(FileInfo::getLineCount));
        this.n = n;
        this.lineCount = new HashMap<>();
        for (int i = maxl / ni; i <= maxl; i += maxl / ni) {
            if (i < maxl) {
                this.lineCount.put(i, new CounterImpl());
            } else {
                this.lineCount.put(Integer.MAX_VALUE, new CounterImpl());
            }
        }
    }

    @Override
    public void submitFileInfo(FileInfo fileInfo) {
        super.submitTask(new DataManagerTaskImpl(this.lineCount, this.topN, fileInfo, this.n));
    }

    @Override
    public HashMap<Integer, Counter> getLineCount() {
        return this.lineCount;
    }

    @Override
    public PriorityBlockingQueue<FileInfo> getTopN() {
        return this.topN;
    }
}
