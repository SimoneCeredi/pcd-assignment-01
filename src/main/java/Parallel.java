import model.pool.ThreadPool;
import model.pool.ThreadPoolImpl;
import model.task.exploredir.ExploreDirTaskImpl;

import java.io.File;

public class Parallel {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool producers = new ThreadPoolImpl(3, "producer");
        ThreadPool consumers = new ThreadPoolImpl(13, "consumer");
        long startTime = System.currentTimeMillis();
        producers.submitTask(new ExploreDirTaskImpl(new File("./files"), producers, consumers));
        producers.onFinish(() -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("Producers finished in " + elapsedTime + "ms");
        });
        consumers.onFinish(() -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println("Producers finished in " + elapsedTime + "ms");
        });
    }
}
