package model.task.datamanager;

import model.Counter;
import model.FileInfo;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

public class DataManagerTaskImpl implements DataManagerTask {
    private final HashMap<Integer, Counter> lineCount;
    private final PriorityBlockingQueue<FileInfo> topN;
    private final FileInfo fileInfo;
    private final int n;

    public DataManagerTaskImpl(HashMap<Integer, Counter> lineCount, PriorityBlockingQueue<FileInfo> topN, FileInfo fileInfo, int n) {
        this.lineCount = lineCount;
        this.topN = topN;
        this.fileInfo = fileInfo;
        this.n = n;
    }

    @Override
    public void run() {
        if (topN.size() < n || this.fileInfo.getLineCount() > Objects.requireNonNull(topN.peek()).getLineCount()) {
            topN.offer(this.fileInfo);
            if (topN.size() > n) {
                topN.poll();
            }

        }
        for (var entry : this.lineCount.entrySet()) {
            if (this.fileInfo.getLineCount() < entry.getKey()) {
                entry.getValue().inc();
            }
        }
//        System.out.println("TopN:");
//        this.topN.forEach(f -> System.out.println("Name: " + f.getFile().getName() + " size: " + f.getLineCount()));
//
//        System.out.println("\n\nLineCount:");
//        this.lineCount.forEach((interval, counter) -> System.out.println(" < " + interval + " -> " + counter.getValue()));
    }
}
