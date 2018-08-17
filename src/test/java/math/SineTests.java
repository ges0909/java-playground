package math;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class SineTests {

  private void trace(Double[] values) {
    final DecimalFormat df = new DecimalFormat("###"/* "###.00" */);
    // @formatter:off
    Arrays.stream(values)
      .map(v -> new BigDecimal(Double.toString(v)))
      .map(bd -> df.format(bd))
      .forEach(bd -> System.out.printf("%s, "/*"%7s"*/, bd));
    // @formatter:on
    System.out.println();
  }

  @Test
  void generateConsolidatedData() {
    final int secondsPerMonth = 64; // 2_592_000; // 30 * 24 * 3600;
    final int standardDeviation = 3; // 68%: 3, 95%: 6, >100%: ~9 => use 10 to avoid negative values
    final Function<Long, Double> sine1 = (t) -> 5 * Math.sin(t) + 5 + 10;
    final Function<Long, Double> sine2 = (t) -> 300 * Math.sin(t / 3600) + 300 + 10;

    // 1st sine wave ('t' symbolizes time)
    Double[] waveOne = Stream.iterate(0L, t -> t + 1).limit(secondsPerMonth).map(sine1).toArray(Double[]::new);
    trace(waveOne);

    // 2nd sine wave
    Double[] waveTwo = Stream.iterate(0L, t -> t + 1).limit(secondsPerMonth).map(sine2).toArray(Double[]::new);
    trace(waveTwo);

    // add waves
    Double[] combinedWaves = new Double[secondsPerMonth];
    Arrays.setAll(combinedWaves, idx -> waveOne[idx] + waveTwo[idx]);
    trace(combinedWaves);

    // add noise by randomizing values following the normal distribution
    Random random = new Random();
    Double[] randomizedWaves = new Double[secondsPerMonth];
    // https://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml
    Arrays.setAll(randomizedWaves, i -> combinedWaves[i] + random.nextGaussian() * standardDeviation);
    trace(randomizedWaves);

    // add anomalies to 10 percent of randomized values
    int[] randomizedAnomaliesIdx = random.ints(secondsPerMonth / 10, 0, secondsPerMonth).toArray();
    for (int idx : randomizedAnomaliesIdx) {
      randomizedWaves[idx] = 1500 * random.nextDouble();
    }
    trace(randomizedWaves);
  }

  @Test
  void generateConsolidatedData2() {
    final Random random = new Random();
    final int secondsPerMonth = 64; // 2_592_000; // 30 * 24 * 3600;
    final int standardDeviation = 3; // 68%: 3, 95%: 6, >100%: ~9 => use 10 to avoid negative values
    final Function<Long, Double> sine1 = (t) -> 5 * Math.sin(t) + 5 + 10;
    final Function<Long, Double> sine2 = (t) -> 300 * Math.sin(t / 3600) + 300 + 10;
    List<Long> anomaliesAt = random.longs(secondsPerMonth / 100, 0, secondsPerMonth).boxed()
        .collect(Collectors.toList());
    Map<Long, Double> samples = new LinkedHashMap<>(); // LinkedHashMap keeps keys in order as they were inserted
    for (long at = 0; at < secondsPerMonth; at++) {
      double value = sine1.apply(at) + sine2.apply(at) + (random.nextGaussian() * standardDeviation);
      if (anomaliesAt.contains(at)) {
        double min = value / 2;
        double max = value * 2;
        value = min + (random.nextDouble() * (max - min) + 1); // nextDouble returns random value between 0.0 and 1.0
      }
      samples.put(at, value);
    }
    trace(samples.values().toArray(new Double[samples.size()]));
  }

  @Test
  void generateConsolidatedData3() {
    final Random random = new Random();
    final int standardDeviation = 3; // 68%: 3, 95%: 6, >100%: ~9 => use 10 to avoid negative values
    final Function<Long, Double> sine1 = (t) -> 5 * Math.sin(t) + 5 + (3 * standardDeviation);
    final Function<Long, Double> sine2 = (t) -> 300 * Math.sin(t / 8 /*3600*/) + 300 + (3 * standardDeviation);

    final Instant beginInstant =  Instant.parse("2018-08-01T00:00:00Z");
    final long begin = beginInstant.getEpochSecond();
    final long end = begin + 1024; // Instant.parse("2018-08-31T23:59:59Z").getEpochSecond();

    LocalDate.ofInstant(beginInstant, ZoneId.systemDefault());

    long size = (end - begin) / 100; // 1% anomalies
    List<Long> anomaliesAt = random.longs(size, begin, end).boxed().collect(Collectors.toList());

    Map<Long, Double> samples = new LinkedHashMap<>(); // LinkedHashMap keeps keys in order as they were inserted
    for (long at = begin; at < end; at++) {
      double value = sine1.apply(at) + sine2.apply(at) + (random.nextGaussian() * standardDeviation);
      if (anomaliesAt.contains(at)) {
        double min = value / 2;
        double max = value * 2;
        value = min + (random.nextDouble() * (max - min) + 1); // nextDouble returns random value between 0.0 and 1.0
      }
      samples.put(at, value);
    }
    System.out.print("x=[");
    // samples.keySet().forEach(k ->  System.out.print(k + ", "));
    LongStream.range(0, end-begin).forEach(k ->  System.out.print(k + ", "));
    System.out.println("];");
    System.out.print("y=[");
    samples.values().forEach(v ->  System.out.print(v + ", "));
    System.out.println("];");
  }
}
