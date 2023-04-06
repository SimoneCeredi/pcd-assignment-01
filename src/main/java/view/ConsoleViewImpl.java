package view;

import model.data.FileInfo;
import model.data.monitor.UnmodifiableCounter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class ConsoleViewImpl implements View {
    @Override
    public void update(Map<Integer, UnmodifiableCounter> lineCounter, Collection<FileInfo> longestFiles) {
        System.out.println("\n\n\n\nLongest Files");
        System.out.println(longestFiles.stream()
                .sorted(Comparator.comparingInt(FileInfo::getLineCount))
                .map(f -> f.getFile().getName() + " -> " + f.getLineCount())
                .collect(Collectors.joining("\n")));
        System.out.println("\n\nLines distribution");
        lineCounter.forEach((lines, counter) -> System.out.println(counter.getValue() + " files of " + lines + " lines"));
    }
}
