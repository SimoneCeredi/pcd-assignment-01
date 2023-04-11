package view;

import model.Model;
import model.data.FileInfo;
import model.data.monitor.Counter;
import model.data.monitor.CounterImpl;

import java.util.Comparator;
import java.util.stream.Collectors;

public class ConsoleViewImpl implements View {
    private static final int UPDATES_BEFORE_PRINT = 100000;
    private final Counter updates = new CounterImpl();

    @Override
    public void modelUpdated(Model model) {
        this.updates.inc();
        if (this.updates.getValue() % UPDATES_BEFORE_PRINT == 0) {
            synchronized (System.out) {
                System.out.println("\n\n\n\nLongest Files");
                System.out.println(model.getLongestFiles().stream()
                        .sorted(Comparator.comparingInt(FileInfo::getLineCount))
                        .map(f -> f.getFile().getAbsolutePath() + " -> " + f.getLineCount())
                        .collect(Collectors.joining("\n")));
                System.out.println("\n\nLines distribution");
                model.getLineCounter().entrySet().stream()
                        .sorted(Comparator.comparingInt(e -> e.getKey().getX()))
                        .forEach(e -> System.out.println(
                                e.getValue().getValue() +
                                        " files in range [" +
                                        e.getKey().getX() +
                                        (e.getKey().getY() == Integer.MAX_VALUE ? "+" : ("," + e.getKey().getY())) +
                                        "]"
                        ));
            }
        }
    }
}
