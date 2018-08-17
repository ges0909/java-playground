package math;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class SineTests {

  private void trace(Double[] values) {
    final DecimalFormat df = new DecimalFormat("###"/*"###.00"*/);
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

    final int STANDARD_DEVIATION = 3;
    final int SECONDS_PER_MONTH = 2_592_000; // 30 * 24 * 3600;

    // TODO: Same period, same phase, ... to add?
    final Function<Long, Double> SINE1 = (t) -> 50 * Math.sin(t) + 55;
    final Function<Long, Double> SINE2 = (t) -> 130 * Math.sin(t) + 135;

    // 1st sine wave ('t' symbolizes time)
    Double[] waveOne = Stream.iterate(0L, t -> t + 1).limit(SECONDS_PER_MONTH).map(SINE1).toArray(Double[]::new);
    //trace(waveOne);

    // 2nd sine wave
    Double[] waveTwo = Stream.iterate(0L, t -> t + 1).limit(SECONDS_PER_MONTH).map(SINE2).toArray(Double[]::new);
    //trace(waveTwo);

    // add waves
    Double[] combinedWaves = new Double[SECONDS_PER_MONTH];
    Arrays.setAll(combinedWaves, idx -> waveOne[idx] + waveTwo[idx]);
    //trace(combinedWaves);

    // add noise by randomizing values following the normal distribution
    Random random = new Random();
    Double[] randomizedWaves = new Double[SECONDS_PER_MONTH];
    // https://www.javamex.com/tutorials/random_numbers/gaussian_distribution_2.shtml
    Arrays.setAll(randomizedWaves, i -> combinedWaves[i] + random.nextGaussian() * STANDARD_DEVIATION);
    //trace(randomizedWaves);

    // add anomalies to 10 percent of randomized values
    int[] randomizedAnomaliesIdx = random.ints(SECONDS_PER_MONTH / 10, 0, SECONDS_PER_MONTH).toArray();
    for (int idx : randomizedAnomaliesIdx) {
      randomizedWaves[idx] = 1500 * random.nextDouble();
    }
    //trace(randomizedWaves);
  }
}
