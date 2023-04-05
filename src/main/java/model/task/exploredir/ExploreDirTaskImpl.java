package model.task.exploredir;

import model.pool.ThreadPool;
import model.task.readlines.ReadLinesTaskImpl;

import java.io.File;

public class ExploreDirTaskImpl implements ExploreDirTask {
    private final File directory;
    private final ThreadPool producersPool;
    private final ThreadPool consumersPool;


    public ExploreDirTaskImpl(File directory, ThreadPool threadPool, ThreadPool consumersPool) {
        this.directory = directory;
        this.producersPool = threadPool;
        this.consumersPool = consumersPool;
    }

    @Override
    public void run() {
        if (this.directory.isDirectory()) {
            File[] files = this.directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        this.producersPool.submitTask(new ExploreDirTaskImpl(file, this.producersPool, this.consumersPool));
                    } else {
                        if (file.getName().endsWith(".java")) {
                            this.consumersPool.submitTask(new ReadLinesTaskImpl(file));
                        }
                    }
                }
            }
        }
    }
}
