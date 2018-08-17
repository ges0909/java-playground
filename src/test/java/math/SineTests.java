package math;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class SineTests {

  private static final int DAYS = 30;
  private static final int HOURS_PER_DAY = 24;
  private static final int SECONDS_PER_HOUR = 3600;
  private static final int SECONDS_PER_MONTH = DAYS * HOURS_PER_DAY * SECONDS_PER_HOUR;

  @Test
  void testSine() {
    Function<Long, Double> sine1 = (l) -> 5 * Math.sin(l) + 5;
    Function<Long, Double> sine2 = (l) -> 15 * Math.sin(l) + 15;
    Double[] wave1 = Stream.iterate(0L, l -> l + 1).limit(SECONDS_PER_MONTH).map(sine1).toArray(Double[]::new);
    Double[] wave2 = Stream.iterate(0L, l -> l + 1).limit(SECONDS_PER_MONTH).map(sine2).toArray(Double[]::new);
    Double[] mixed = new Double[SECONDS_PER_MONTH];
    Arrays.setAll(mixed, i -> wave1[i] + wave2[i]);

    int max = 10;
    int min = 5;
    int totalNumber = 10;

    Random random = new Random();
    double randomInt = min + random.nextGaussian(max - min + 1);

    DoubleStream stream = random.doubles(totalNumber, min, max);
    stream.forEach(System.out::println);

    // https://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml
  }
}