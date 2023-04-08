package jpf;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FirstAssignment {

    private static final Random random = new Random();
    static ThreadPool explorers = new ThreadPool(2);
    static ThreadPool fileReaders = new ThreadPool(2);
    static ThreadPool dataManagers = new ThreadPool(1);
    static ThreadPool interfaceVisualizers = new ThreadPool(1);

    public static void main(String[] args) {
        explorers.submitTask(new ExplorerTask(2));
    }

    private static class ThreadPool {
        private final List<Thread> threadList = new LinkedList<>();
        private final BlockingQueue<Runnable> taskQueue;

        public ThreadPool(int numThreads) {
            taskQueue = new LinkedBlockingQueue<>();
            for (int i = 0; i < numThreads; i++) {
                this.threadList.add(new Thread(() -> {
                    for (int j = 0; j < 2; j++) {
                        try {
                            taskQueue.take().run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }));
                this.threadList.get(i).start();
            }
        }

        public void submitTask(Runnable task) {

            this.taskQueue.add(task);
        }
    }

    private static class ExplorerTask implements Runnable {
        private final int data;

        private ExplorerTask(int data) {
            this.data = data;
        }

        @Override
        public void run() {
            consume(this.data);
            // produce a new folder
            explorers.submitTask(new ExplorerTask(produce()));
            // produce a new file
            fileReaders.submitTask(new ReaderTask(produce()));
        }

        private void consume(int data) {
            System.out.println("Explorer has consumed " + data);
        }

        private int produce() {
            int newData = random.nextInt();
            System.out.println("Explorer has produced " + newData);
            return newData;
        }
    }

    private static class ReaderTask implements Runnable {
        private final int data;

        private ReaderTask(int data) {
            this.data = data;
        }

        @Override
        public void run() {
            consume(this.data);
            dataManagers.submitTask(new DataTask(produce()));
        }

        private void consume(int data) {
            System.out.println("Reader has consumed " + data);
        }

        private int produce() {
            int newData = random.nextInt();
            System.out.println("Reader has produced " + newData);
            return newData;
        }
    }

    private static class DataTask implements Runnable {
        private final int data;

        private DataTask(int data) {
            this.data = data;
        }

        @Override
        public void run() {
            consume(this.data);
            interfaceVisualizers.submitTask(new InterfaceTask(produce()));
        }

        private void consume(int data) {
            System.out.println("DataTask has consumed " + data);
        }

        private int produce() {
            int newData = random.nextInt();
            System.out.println("DataTask has produced " + newData);
            return newData;
        }
    }

    private static class InterfaceTask implements Runnable {
        private final int data;

        private InterfaceTask(int data) {
            this.data = data;
        }

        @Override
        public void run() {
            consume(this.data);
        }

        private void consume(int data) {
            System.out.println("Interface has consumed " + data);
        }

    }
}
