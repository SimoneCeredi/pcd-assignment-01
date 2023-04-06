package model.pool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolImpl implements ThreadPool {
    private final List<Thread> threadList = new LinkedList<>();
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final CountDownLatch latch;
    private volatile boolean shouldStop = false;

    public ThreadPoolImpl(int numThreads, String name) {
        this.latch = new CountDownLatch(numThreads);
        for (int i = 0; i < numThreads; i++) {
            this.threadList.add(new Thread(() -> {
                while (!shouldStop) {
                    try {
                        taskQueue.take().run();
                        this.latch.countDown();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, name + "-" + i));
            this.threadList.get(i).start();
        }
    }

    @Override
    public void submitTask(Runnable task) {
        this.taskQueue.add(task);
    }

    @Override
    public void clearTasks() {
        this.taskQueue.clear();
    }

    @Override
    public void onFinish(Runnable callback) {
        while (true) {
            if (taskQueue.isEmpty() && latch.getCount() == 0) {
                callback.run();
                return;
            } else {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void stop() {
        this.shouldStop = true;
        for (Thread t : this.threadList) {
            t.interrupt();
        }
    }

}
