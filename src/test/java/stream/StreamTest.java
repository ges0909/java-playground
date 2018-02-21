package stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreamTest {

  List<Long> list;

  @BeforeEach
  void setUp() {
    list = new ArrayList<>();
    list.add(4L);
    list.add(92L);
    list.add(100L);
  }

  /**
   * Takes a List of Long's and reduces it to comma-separated string.
   */
  @Test
  void reduceListToString() {
    String result = list.stream().map(String::valueOf).reduce((a, b) -> a + ", " + b).orElseGet(String::new);
    assertEquals("4, 92, 100", result);
  }

  /**
   * Takes a List of Long's and converts it to an array of Long's.
   */
  @Test
  void reduceListToArray() {
    Long[] result = list.stream().toArray(Long[]::new);
    assertTrue(Arrays.equals(new Long[] { 4L, 92L, 100L }, result));
  }

  /**
   *  Takes a List of Long's and converts it to an array of scalar long's.
   */
  @Test
  void reduceListToArray2() {
    long[] result = list.stream().mapToLong(l -> l).toArray(); // argument to 'mapToLong' is a ToLongFunction, which has a 'long' as result type
    assertTrue(Arrays.equals(new long[] { 4L, 92L, 100L }, result));
  }
}