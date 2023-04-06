package model.pool;

public interface ThreadPool {
    void submitTask(Runnable task);

    void clearTasks();

    void onFinish(Runnable callback);

    void stop();
}
