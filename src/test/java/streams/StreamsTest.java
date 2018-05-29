package streams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
}
