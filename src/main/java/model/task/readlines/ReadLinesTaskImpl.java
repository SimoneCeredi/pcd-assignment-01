package model.task.readlines;

import utils.Files;

import java.io.File;

public class ReadLinesTaskImpl implements ReadLinesTask {
    private final File file;

    public ReadLinesTaskImpl(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        final int fileLength = Files.countLines(file);
//        System.out.println("Read " + this.file.getName() + " -> " + fileLength);
    }
}
