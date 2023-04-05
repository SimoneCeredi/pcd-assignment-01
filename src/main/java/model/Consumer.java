package model;

import utils.Files;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable {
    private final BlockingQueue<File> queue;
    private final AtomicBoolean producerFinished;

    public Consumer(BlockingQueue<File> queue, AtomicBoolean producerFinished) {
        this.queue = queue;
        this.producerFinished = producerFinished;
    }

    @Override
    public void run() {
        this.consume();
    }

    private void consume() {
        while (!(this.queue.isEmpty() && this.producerFinished.get())) {
            File file;
            int fileLength;
            try {
                file = queue.take();
                fileLength = Files.countLines(file);
//                System.out.println("\tConsumed: " + file.getAbsolutePath() + " -> " + fileLength);
                // TODO: save file length
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
