package model;

import utils.Files;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<File> queue;

    public Consumer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        this.consume();
    }

    private void consume() {
        while (true) {
            File file;
            int fileLength;
            try {
                file = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            fileLength = Files.countLines(file);
            // TODO: save file length
        }
    }
}
