import model.pool.DataManagersPool;
import model.pool.DataManagersPoolImpl;
import model.pool.ThreadPool;
import model.pool.ThreadPoolImpl;
import model.task.exploredir.ExploreDirTaskImpl;

import java.io.File;

public class Parallel {

    public static void main(String[] args) throws InterruptedException {
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
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("Producers finished in " + elapsedTime + "ms");
            producers.stop();
        });
        consumers.onFinish(() -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("Producers finished in " + elapsedTime + "ms");
            consumers.stop();
        });
    }
}
