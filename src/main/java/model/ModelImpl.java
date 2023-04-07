package model;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;
import model.pool.DataManagersPool;
import model.pool.DataManagersPoolImpl;
import model.pool.ThreadPool;
import model.pool.ThreadPoolImpl;
import model.task.exploredir.ExploreDirTaskImpl;
import observers.ModelObserver;
import utils.Pair;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelImpl implements UpdatableModel, Model {
    private static final int NUM_EXPLORERS_THREADS = 2;
    private static final int NUM_COUNTERS_THREADS = 4;
    private static final int NUM_DM_THREADS = 2;
    private final List<ModelObserver> observers;
    private ThreadPool fileSystemExplorers;
    private ThreadPool lineCounters;
    private DataManagersPool dataManagersPool;
    private File directory;
    private int ni;
    private int maxl;
    private int n;

    public ModelImpl(File d, int ni, int maxl, int n) {
        this.observers = new LinkedList<>();
        this.directory = d;
        this.ni = ni;
        this.maxl = maxl;
        this.n = n;

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
        this.fileSystemExplorers = new ThreadPoolImpl(NUM_EXPLORERS_THREADS, "fs-expl");
        this.lineCounters = new ThreadPoolImpl(NUM_COUNTERS_THREADS, "l-count");
        this.dataManagersPool = new DataManagersPoolImpl(this, NUM_DM_THREADS, "data-man", ni, maxl, n);
        this.fileSystemExplorers.submitTask(new ExploreDirTaskImpl(this.directory, this.fileSystemExplorers, this.lineCounters, this.dataManagersPool));
    }

    @Override
    public void stop() {
        this.fileSystemExplorers.clearTasks();
        this.lineCounters.clearTasks();
        this.dataManagersPool.clearTasks();
        this.fileSystemExplorers.stop();
        this.lineCounters.stop();
        this.dataManagersPool.stop();
    }

    @Override
    public void onFinish(Runnable callback) {
        this.fileSystemExplorers.onFinish(() -> this.lineCounters.onFinish(() -> this.dataManagersPool.onFinish(callback)));
    }

    @Override
    public void update() {
        this.notifyObservers();
    }

    @Override
    public void addObserver(ModelObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void changeParams(File d, int ni, int maxl, int n) {
        this.stop();
        this.directory = d;
        this.ni = ni;
        this.maxl = maxl;
        this.n = n;
    }

    private void notifyObservers() {
        for (var observer : this.observers) {
            observer.modelUpdated(this);
        }
    }
}
