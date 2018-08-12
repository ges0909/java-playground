package streams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class ParallelStreamTests {

  private Integer sumInt(Map.Entry<Integer, List<Integer>> entry) {
    return entry.getValue().stream().mapToInt(Integer::intValue).sum();
  }

  private Long sumLong(Map.Entry<Long, List<Long>> entry) {
    return entry.getValue().stream().mapToLong(Long::longValue).sum();
  }

  @Test
  public void testIntegerParallelStream() {
    Integer[] integerArray = { 1, 1, 2, 2, 2, 3, 4, 4, 4, 4, 4, 5, 6, 7, 7, 8, 9, 7, 7, 4, 5 };
    // @formatter:off
    Stream.of(integerArray)
      .parallel()
      .collect(Collectors.groupingByConcurrent(Integer::intValue)) // => ConcurrentMap<Integer, List<Integer>>
      .entrySet()
      .stream()
      // .peek(System.out::println)
      .map(this::sumInt)
      // .collect(Collectors.toList())
      .forEachOrdered(System.out::println);
    // @formatter:on
  }

  @Test
  public void testLongParallelStream() {
    // @formatter:off
    Stream.iterate(0L, l -> l + 1).limit(1_000_000)
      .parallel()
      .collect(Collectors.groupingByConcurrent(Long::longValue))
      .entrySet()
      .stream()
      .map(this::sumLong)
  //    .collect(Collectors.toList())
      .forEachOrdered(System.out::println);
    // @formatter:on
  }

  @Test
  public void testFileStream() throws URISyntaxException, IOException {
    Path file = Paths.get(getClass().getClassLoader().getResource("streams/test-100_000.log").toURI());
    try (Stream<String> lines = Files.lines(file)) {
      List<Entry<Long, Integer>> samples = lines
      // @formatter:off
        .parallel()
        .map(LogEntry::new)
        .filter(LogEntry::isValid)
        .filter(LogEntry::isError)
        .collect(Collectors.groupingByConcurrent(LogEntry::getTimestamp))// groups log entries by timestamp
        .entrySet() // <= Map<Long, List<LogEntry>>
        .stream()
        .map(e -> new AbstractMap.SimpleEntry<Long, Integer>(e.getKey(), e.getValue().size())) // <= Map.Entry<Long, Integer>
        .sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toList()); // Map.Entry::getKey, Map.Entry::getValue
      // @formatter:on
      samples.forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
    }
  }
}
