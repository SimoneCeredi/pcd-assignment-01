import model.data.FileInfo;
import model.pool.DataManagersPool;
import model.pool.DataManagersPoolImpl;
import model.pool.ThreadPool;
import model.pool.ThreadPoolImpl;
import model.task.exploredir.ExploreDirTaskImpl;

import java.io.File;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Parallel {

    public static void main(String[] args) {
        String d = args[0]; // D directory presente sul file system
        int ni = Integer.parseInt(args[1]); // NI numero di intervalli
        int maxl = Integer.parseInt(args[2]); // MAXL numero numero massimo di linee di codice per delimitare l'estremo sinistro dell'ultimo intervallo
        int n = Integer.parseInt(args[3]); // N sorgenti con il numero maggiore di linee di codice
        ThreadPool producers = new ThreadPoolImpl(3, "producer");
        ThreadPool consumers = new ThreadPoolImpl(13, "consumer");
        DataManagersPool dataManagersPool = new DataManagersPoolImpl(3, "data-man", ni, maxl, n);
        long startTime = System.currentTimeMillis();
        producers.submitTask(new ExploreDirTaskImpl(new File(d), producers, consumers, dataManagersPool));
//        Thread.sleep(100);
//        System.exit(0);
        producers.onFinish(() -> {
            long producersTime = System.currentTimeMillis() - startTime;
            System.out.println("Producers finished in " + producersTime + "ms");
            producers.stop();
            consumers.onFinish(() -> {
                long consumersTime = System.currentTimeMillis() - startTime;
                System.out.println("Producers finished in " + consumersTime + "ms");
                consumers.stop();
                dataManagersPool.onFinish(() -> {
                    long dataManagersTime = System.currentTimeMillis() - startTime;
                    System.out.println("Producers finished in " + dataManagersTime + "ms");
                    dataManagersPool.stop();
                    System.out.println("LineCounter");
                    dataManagersPool.getLineCounter().forEach((l, c) -> System.out.println("of " + l + " lines -> " + c.getValue()));
                    System.out.println("LongestFiles");
                    System.out.println(dataManagersPool.getLongestFiles().stream().sorted(Comparator.comparingInt(FileInfo::getLineCount)).map(f -> f.getFile().getName() + " -> " + f.getLineCount()).collect(Collectors.joining("\n")));
                });
            });
        });
    }
}
