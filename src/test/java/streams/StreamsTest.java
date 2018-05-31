package streams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

class StreamsTest {
  @Data
  @AllArgsConstructor
  class Pair {
    int id;
    String name;
  }

  @Data
  @AllArgsConstructor
  class Point {
    long timestamp;
    double value;
  }

  // Reduces a list of integers to a comma-separated string.
  @Test
  void reduceListToString() {
    List<Integer> list = List.of(1, 2, 3, 5, 8, 13, 21);
    String result = list.stream().map(String::valueOf).reduce((a, b) -> a + ", " + b).orElseGet(String::new);
    assertEquals("1, 2, 3, 5, 8, 13, 21", result);
  }

  // Converts a list of integers to an array.
  @Test
  void reduceListToArray() {
    List<Integer> list = List.of(1, 2, 3, 5, 8, 13, 21);
    Integer[] result = list.stream().toArray(Integer[]::new);
    assertTrue(Arrays.equals(new Integer[] { 1, 2, 3, 5, 8, 13, 21 }, result));
  }

  // Converts a list of integers to an scalar array.
  @Test
  void reduceListToArray2() {
    List<Integer> list = List.of(1, 2, 3, 5, 8, 13, 21);
    int[] result = list.stream().mapToInt(l -> l).toArray();
    assertTrue(Arrays.equals(new int[] { 1, 2, 3, 5, 8, 13, 21 }, result));
  }

  @Test
  void groupingBy() {
    List<Pair> pairs = List.of(new Pair(2, "B"), new Pair(1, "A"), new Pair(3, "C"), new Pair(1, "AA"),
        new Pair(2, "BB"));
    Map<Integer, List<Pair>> groupedByPairs = pairs.stream().collect(Collectors.groupingBy(Pair::getId));
    assertEquals(groupedByPairs.toString(),
        "{1=[StreamsTest.Pair(id=1, name=A), StreamsTest.Pair(id=1, name=AA)], 2=[StreamsTest.Pair(id=2, name=B), StreamsTest.Pair(id=2, name=BB)], 3=[StreamsTest.Pair(id=3, name=C)]}");
  }

  @Test
  void sorted() {
    List<Pair> pairs = List.of(new Pair(2, "B"), new Pair(1, "A"), new Pair(3, "C"));
    List<Pair> sortedPairs = pairs.stream().sorted(Comparator.comparing(Pair::getId)).collect(Collectors.toList());
    assertEquals(sortedPairs.toString(),
        "[StreamsTest.Pair(id=1, name=A), StreamsTest.Pair(id=2, name=B), StreamsTest.Pair(id=3, name=C)]");
  }

  @Test
  void accummulate() {
    List<Integer> list = List.of(1, 2, 3, 4, 5);
    List<Long> accuList = new ArrayList<>();
    long accu = 0;
    for (Integer i : list) {
      accu = accu + i;
      accuList.add(accu);
    }
    System.out.println(accuList.toString());
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