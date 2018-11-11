package stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.eq;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

class StreamTest {

  class Pair {
    int id;
    String name;

    Pair(int id, String name) {
      this.id = id;
      this.name = name;
    }

    int getId() {
      return this.id;
    }
  }

  class Point {
    long timestamp;
    double value;

    Point(long timestamp, double value) {
      this.timestamp = timestamp;
      this.value = value;
    }

    double getValue() {
      return this.value;
    }
  }

  @Test
  void listToString() {
    List<Integer> list = List.of(1, 2, 3, 5, 8, 13, 21);
    String result = list.stream().map(String::valueOf).reduce((a, b) -> a + ", " + b).orElseGet(String::new);
    assertThat(result).isEqualTo("1, 2, 3, 5, 8, 13, 21");
  }

  @Test
  void listToArray() {
    List<Integer> list = List.of(1, 2, 3, 5, 8, 13, 21);
    // variant 1
    Integer[] result = list.stream().toArray(Integer[]::new);
    assertThat(result).isEqualTo(new Integer[] { 1, 2, 3, 5, 8, 13, 21 });
    // variant 2
    int[] result2 = list.stream().mapToInt(l -> l).toArray();
    assertThat(result2).isEqualTo(new Integer[] { 1, 2, 3, 5, 8, 13, 21 });

  }

  /**
   * Returns the number of the month ranging from 0..11.
   */
  private int month(Point point) {
    Instant instant = Instant.ofEpochMilli(point.timestamp);
    LocalDateTime dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    return dateTime.getMonth().ordinal();
  }

  @Test
  void average() {
    long jan16 = LocalDate.of(2016, Month.JANUARY, 31).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli();
    long jan17 = LocalDate.of(2017, Month.JANUARY, 31).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli();
    long jan18 = LocalDate.of(2018, Month.JANUARY, 31).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli();
    long feb16 = LocalDate.of(2016, Month.FEBRUARY, 29).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli();
    long feb17 = LocalDate.of(2017, Month.FEBRUARY, 28).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli();
    long feb18 = LocalDate.of(2018, Month.FEBRUARY, 28).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        .toEpochMilli();

    Point[] _points = { new Point(jan16, 1.0), new Point(feb16, 2.0), new Point(jan17, 3.0), new Point(feb17, 4.0),
        new Point(jan18, 5.0), new Point(feb18, 6.0) };
    List<Point> points = Arrays.asList(_points);

    Map<Integer, Double> avgMap = new HashMap<>();
    Map<Integer, List<Point>> sortedByMonth = points.stream().collect(Collectors.groupingBy(this::month));
    for (Entry<Integer, List<Point>> entry : sortedByMonth.entrySet()) {
      double avg = entry.getValue().stream().mapToDouble(Point::getValue).average().getAsDouble();
      avgMap.put(entry.getKey(), avg);
    }
  }

  @Test
  void groupingBy() {
    List<Pair> pairs = List.of(new Pair(1, "A"), new Pair(1, "B"), new Pair(2, "C"), new Pair(3, "D"));
    Map<Integer, List<Pair>> groupedBy = pairs.stream().collect(Collectors.groupingBy(Pair::getId));
    assertThat(groupedBy.size()).isEqualTo(3);
    assertThat(groupedBy.get(1).size()).isEqualTo(2);
    assertThat(groupedBy.get(2).size()).isEqualTo(1);
    assertThat(groupedBy.get(3).size()).isEqualTo(1);
  }

  @Test
  void sorted() {
    List<Integer> list = List.of(2, 3, 1);
    List<Integer> sorted = list.stream().sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList());
    assertThat(sorted).isEqualTo(List.of(1, 2, 3));
  }

  @Test
  void partion() {
    final int size = 2;
    final AtomicInteger counter = new AtomicInteger(0);
    List<Integer> list = List.of(1, 2, 3, 4, 5);
    Collection<List<Integer>> actual = list.stream().collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size)).values();
    assertThat(actual.size()).isEqualTo(3);
    assertThat(new ArrayList<>(actual).get(0).size()).isEqualTo(2);
    assertThat(new ArrayList<>(actual).get(1).size()).isEqualTo(2);
    assertThat(new ArrayList<>(actual).get(2).size()).isEqualTo(1);
  }
}
