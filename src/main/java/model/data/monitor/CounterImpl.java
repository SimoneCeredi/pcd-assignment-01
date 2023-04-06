package model.data.monitor;

public class CounterImpl implements Counter {
    int value;

    @Override
    public synchronized void inc() {
        this.value++;
    }

    @Override
    public synchronized int getValue() {
        return this.value;
    }
}
