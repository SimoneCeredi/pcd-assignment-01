package model.pool;

public interface ThreadPool {
    void submitTask(Runnable task);

    void onFinish(Runnable callback) throws InterruptedException;


}
