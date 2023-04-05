package model.task.exploredir;

import model.pool.DataManagersPool;
import model.pool.ThreadPool;
import model.task.readlines.ReadLinesTaskImpl;

import java.io.File;

public class ExploreDirTaskImpl implements ExploreDirTask {
    private final File directory;
    private final ThreadPool producersPool;
    private final ThreadPool consumersPool;
    private final DataManagersPool dataManagerPool;


    public ExploreDirTaskImpl(File directory, ThreadPool threadPool, ThreadPool consumersPool, DataManagersPool dataManagerPool) {
        this.directory = directory;
        this.producersPool = threadPool;
        this.consumersPool = consumersPool;
        this.dataManagerPool = dataManagerPool;
    }

    @Override
    public void run() {
        if (this.directory.isDirectory()) {
            File[] files = this.directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        this.producersPool.submitTask(new ExploreDirTaskImpl(file, this.producersPool, this.consumersPool, dataManagerPool));
                    } else {
                        if (file.getName().endsWith(".java")) {
                            this.consumersPool.submitTask(new ReadLinesTaskImpl(file, dataManagerPool));
                        }
                    }
                }
            }
        }
    }
}
