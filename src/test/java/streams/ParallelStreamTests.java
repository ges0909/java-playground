package streams;

import java.util.List;
import java.util.Map;
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
  public void testIntegerParallel() {
    Integer[] integerArray = { 1, 1, 2, 2, 2, 3, 4, 4, 4, 4, 4, 5, 6, 7, 7, 8, 9, 7, 7, 4, 5 };
    // @formatter:off
    List<Integer> samples = Stream.of(integerArray)
      .parallel()
      .collect(Collectors.groupingByConcurrent(Integer::intValue)) // => ConcurrentMap<Integer, List<Integer>>
      .entrySet()
      .stream()
      .map(this::sumInt)
      .collect(Collectors.toList());
    // @formatter:on
    samples.forEach(s -> System.out.println(s));
  }

  @Test
  public void testLongParallel() {
    Stream<Long> longStream = Stream.iterate(0L, l -> l + 1).limit(1_000_000);
    // @formatter:off
    List<Long> samples = longStream
      .parallel()
      .collect(Collectors.groupingByConcurrent(Long::longValue))
      .entrySet()
      .stream()
      .map(this::sumLong)
      .collect(Collectors.toList());
    // @formatter:on
    samples.forEach(s -> System.out.println(s));
  }
}