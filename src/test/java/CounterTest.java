import model.Counter;
import model.CounterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CounterTest {

    private Counter counter;

    @BeforeEach
    void setUp() {
        this.counter = new CounterImpl();
    }

    @Test
    void testCounter() {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                counter.inc();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                counter.inc();
            }
        });

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1000, this.counter.getValue());

    }

}
