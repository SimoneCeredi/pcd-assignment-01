package model.data.monitor;

import model.data.FileInfo;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class LongestFilesQueueImpl implements LongestFilesQueue {
    private final int filesToKeep;
    private final Queue<FileInfo> queue = new PriorityQueue<>(Comparator.comparingInt(FileInfo::getLineCount));

    public LongestFilesQueueImpl(int filesToKeep) {
        this.filesToKeep = filesToKeep;
    }

    @Override
    public synchronized void put(FileInfo fileInfo) {
        if (this.queue.size() < this.filesToKeep || fileInfo.getLineCount() > Objects.requireNonNull(this.queue.peek()).getLineCount()) {
            this.queue.offer(fileInfo);
            if (this.queue.size() > this.filesToKeep) {
                this.queue.poll();
            }
        }
    }
}
