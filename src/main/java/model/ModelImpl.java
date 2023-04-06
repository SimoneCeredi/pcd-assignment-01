package model;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;
import model.pool.DataManagersPool;
import model.pool.DataManagersPoolImpl;
import model.pool.ThreadPool;
import model.pool.ThreadPoolImpl;
import model.task.exploredir.ExploreDirTaskImpl;
import utils.Pair;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public class ModelImpl implements Model {
    public static final int NUM_EXPLORERS_THREADS = 3;
    private final ThreadPool fileSystemExplorers;
    private final ThreadPool lineCounters;
    private final DataManagersPool dataManagersPool;
    private File directory;

    public ModelImpl(File d, int ni, int maxl, int n) {
        this.fileSystemExplorers = new ThreadPoolImpl(NUM_EXPLORERS_THREADS, "fs-expl");
        this.lineCounters = new ThreadPoolImpl(18, "l-count");
        this.dataManagersPool = new DataManagersPoolImpl(3, "data-man", ni, maxl, n);
        this.directory = d;
    }

    @Override
    public Map<Pair<Integer, Integer>, UnmodifiableCounter> getLineCounter() {
        return this.dataManagersPool.getLineCounter();
    }

    @Override
    public Collection<FileInfo> getLongestFiles() {
        return this.dataManagersPool.getLongestFiles();
    }

    @Override
    public void start() {
        this.fileSystemExplorers.submitTask(new ExploreDirTaskImpl(this.directory, this.fileSystemExplorers, this.lineCounters, this.dataManagersPool));
    }

    @Override
    public void stop() {
        this.fileSystemExplorers.stop();
        this.lineCounters.stop();
        this.dataManagersPool.stop();
    }

    @Override
    public void onFinish(Runnable callback) {
        this.fileSystemExplorers.onFinish(() -> this.lineCounters.onFinish(() -> this.dataManagersPool.onFinish(callback)));
    }


    @Override
    public void changeDir(File newDir) {
        this.directory = newDir;
    }
}
