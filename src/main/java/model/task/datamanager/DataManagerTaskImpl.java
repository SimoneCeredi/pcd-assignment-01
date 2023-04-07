package model.task.datamanager;

import model.UpdatableModel;
import model.data.FileInfo;
import model.data.LineCounter;
import model.data.monitor.LongestFilesQueue;

public class DataManagerTaskImpl implements DataManagerTask {
    private final LineCounter lineCounter;
    private final LongestFilesQueue longestFiles;
    private final FileInfo fileInfo;
    private final UpdatableModel model;

    public DataManagerTaskImpl(UpdatableModel model, LineCounter lineCounter, LongestFilesQueue longestFiles, FileInfo fileInfo) {
        this.lineCounter = lineCounter;
        this.longestFiles = longestFiles;
        this.fileInfo = fileInfo;
        this.model = model;
    }

    @Override
    public void run() {
        this.lineCounter.store(this.fileInfo);
        this.longestFiles.put(this.fileInfo);
        this.model.update();
//        System.out.println("\n\n\n\nTopN:");
//        this.topN.forEach(f -> System.out.println("Name: " + f.getFile().getName() + " size: " + f.getLineCount()));
//
//        System.out.println("\n\nLineCount:");
//        this.lineCount.forEach((interval, counter) -> System.out.println(" < " + interval + " -> " + counter.getValue()));
    }
}
