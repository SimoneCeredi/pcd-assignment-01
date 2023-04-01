package model;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<File> queue;
    private final String startingDir;

    public Producer(BlockingQueue<File> queue, String startingDir) {
        this.queue = queue;
        this.startingDir = startingDir;
    }

    @Override
    public void run() {
        this.getAllFiles(new File(this.startingDir));
    }

    private void getAllFiles(File folder) {
        if (folder.isDirectory()) {
            File[] subFiles = folder.listFiles();
            if (subFiles != null) {
                for (File file : subFiles) {
                    if (file.isDirectory()) {
                        this.getAllFiles(file);
                    } else {
                        if (file.getName().endsWith(".java")) {
                            try {
                                queue.put(file);
                                System.out.println("Produced: " + file.getName());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
