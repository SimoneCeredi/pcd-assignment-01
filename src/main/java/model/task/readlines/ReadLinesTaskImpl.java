package model.task.readlines;

import model.FileInfo;
import model.pool.DataManagersPool;
import utils.Files;

import java.io.File;

public class ReadLinesTaskImpl implements ReadLinesTask {
    private final File file;
    private final DataManagersPool dataManagersPool;

    public ReadLinesTaskImpl(File file, DataManagersPool dataManagerPool) {
        this.file = file;
        this.dataManagersPool = dataManagerPool;
    }

    @Override
    public void run() {
        final int fileLength = Files.countLines(file);
        this.dataManagersPool.submitFileInfo(new FileInfo(this.file, fileLength));
    }
}
