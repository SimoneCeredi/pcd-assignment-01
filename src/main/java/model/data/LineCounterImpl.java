package model.data;

import model.data.monitor.Counter;
import model.data.monitor.CounterImpl;

import java.util.HashMap;

public class LineCounterImpl implements LineCounter {
    private final HashMap<Integer, Counter> map;

    public LineCounterImpl(int intervals, int maxLines) {
        this.map = new HashMap<>();
        for (int i = maxLines / intervals; i <= maxLines; i += maxLines / intervals) {
            this.map.put(i < maxLines ? i : Integer.MAX_VALUE, new CounterImpl());
        }
    }

    @Override
    public void store(FileInfo fileInfo) {
        for (var entry : this.map.entrySet()) {
            if (fileInfo.getLineCount() < entry.getKey()) {
                entry.getValue().inc();
            }
        }

    }
}
