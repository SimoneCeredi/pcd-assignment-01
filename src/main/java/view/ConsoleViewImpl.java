package view;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;
import utils.Pair;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleViewImpl implements View {
    @Override
    public void update(Map<Pair<Integer, Integer>, UnmodifiableCounter> lineCounter, Collection<FileInfo> longestFiles) {
        System.out.println("\n\n\n\nLongest Files");
        System.out.println(longestFiles.stream()
                .sorted(Comparator.comparingInt(FileInfo::getLineCount))
                .map(f -> f.getFile().getAbsolutePath() + " -> " + f.getLineCount())
                .collect(Collectors.joining("\n")));
        System.out.println("\n\nLines distribution");
        lineCounter.entrySet().stream()
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
