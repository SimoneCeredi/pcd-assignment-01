package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Files {
    public static List<File> getFiles(String folderPath) {
        List<File> files = new ArrayList<>();
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] subFiles = folder.listFiles();
            if (subFiles != null) {
                for (File file : subFiles) {
                    if (file.isDirectory()) {
                        files.addAll(getFiles(file.getAbsolutePath()));
                    } else {
                        if (file.getName().endsWith(".java")) {
                            files.add(file);
                        }
                    }
                }
            }
        }
        return files;
    }

    public static int countLines(File file) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
