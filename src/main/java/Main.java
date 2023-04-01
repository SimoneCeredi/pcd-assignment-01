import utils.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static final String D = "./files";

    public static void main(String[] args) {
        List<File> files = Files.getFiles(D);
        System.out.println(files.size());
        List<Integer> filesLength = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (File file : files) {
            filesLength.add(Files.countLines(file));
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("Elapsed time " + elapsedTime + "ms");
        Map<Integer, Long> counts = filesLength.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        counts.forEach((n, tot) -> System.out.println(n + "->" + tot));
    }
}
