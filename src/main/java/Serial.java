import utils.Files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Serial {

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
        AtomicInteger i = new AtomicInteger();
        Map<Integer, Long> counts = filesLength.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        counts.forEach((n, tot) -> {
            System.out.println(n + "->" + tot);
            i.addAndGet(Math.toIntExact(tot));
        });
        System.out.println("Tot files " + i.get());
        System.out.println("Elapsed time " + elapsedTime + "ms");
    }
}
