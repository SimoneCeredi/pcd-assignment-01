import model.Consumer;
import model.Producer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Parallel {

    private static final int QUEUE_CAPACITY = 50;
    private static final List<Thread> consumers = new ArrayList<>();
    private static Thread producer;
    private static final BlockingQueue<File> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    private static final AtomicBoolean producerFinished = new AtomicBoolean();


    public static void main(String[] args) {
        producer = new Thread(new Producer(queue, "./files"));
        for (int i = 0; i < 16; i++) {
            consumers.add(new Thread(new Consumer(queue, producerFinished)));
        }
        producer.start();
        for (Thread consumer : consumers) {
            consumer.start();
        }
        try {
            producer.join();
            producerFinished.set(true);
            for (Thread consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
