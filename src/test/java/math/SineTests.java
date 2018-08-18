package math;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class SineTests {

  private static final String home = System.getProperty("user.home");
  private static final Path outputDir = Paths.get(home + "/Desktop/testOutput");

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

  private void writeToScilabFile(Path outputFile, Map<Long, Double> samples) throws IOException {
    final StringBuffer xbuf = new StringBuffer();
    LongStream.range(0, samples.size()).forEach(l -> xbuf.append(l).append(","));
    xbuf.setLength(xbuf.length() - 1);
    String xvar = "x=[" + xbuf.toString() + "];";

    final StringBuffer ybuf = new StringBuffer();
    samples.values().forEach(v -> ybuf.append(v).append(","));
    ybuf.setLength(ybuf.length() - 1);
    String yvar = "y=[" + ybuf.toString() + "];";

    String content = xvar.toString() + "\n" + yvar.toString() + "\n" + "plot(x,y);" + "\n";

    Files.write(outputFile, content.toString().getBytes());
  }

  private void writeToFile(Path outputFile, Map<Long, Double> samples) throws IOException {
    StringBuffer sb = new StringBuffer();
    for (Map.Entry<Long, Double> sample : samples.entrySet()) {
      String line = sample.getKey() + " " + sample.getValue() + "\n";
      sb.append(line);
    }
    Files.write(outputFile, sb.toString().getBytes());
  }

  @Test
  void testGenerateConsolidatedData() {
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
  void testGenerateConsolidatedData2() {
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
  public void testGenerator() throws IOException {

    final Random random = new Random();
    final int standardDeviation = 3; // 68%: 3, 95%: 6, >100%: ~9 => use 10 to avoid negative values
    // @formatter:off
    final Function<Long, Double> sine1 = (t) -> 200 * Math.sin((2 * Math.PI) /  3600           * t) + 5   + (3 * standardDeviation);
    final Function<Long, Double> sine2 = (t) -> 300 * Math.sin((2 * Math.PI) / (3600 * 24 * 7) * t) + 300 + (3 * standardDeviation);
    // @formatter:on

    LocalDate startDate = LocalDate.parse("2018-08-01");
    LocalDate endDate = LocalDate.parse("2018-08-31");

    for (LocalDate curDate = startDate; curDate.isBefore(endDate)
        || curDate.isEqual(endDate); curDate = curDate.plusDays(1)) {

      long atBeginOfDay = curDate.atTime(LocalTime.MIN).toEpochSecond(ZoneOffset.of("+2"));
      long atEndOfDay = curDate.atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.of("+2"));

      long numberOfAnomalies = (atEndOfDay - atBeginOfDay) / 10_000; // 0,0001 percent anomalies
      List<Long> anomaliesAt = random.longs(numberOfAnomalies, atBeginOfDay, atEndOfDay).boxed()
          .collect(Collectors.toList());

      Map<Long, Double> samples = new LinkedHashMap<>(); // keeps keys in order as they were inserted
      for (long at = atBeginOfDay; at <= atEndOfDay; at++) {
        double value = sine1.apply(at) + sine2.apply(at) + (random.nextGaussian() * standardDeviation);
        if (anomaliesAt.contains(at)) {
          double min = 0;
          double max = value * 2;
          value = min + (random.nextDouble() /* 0.0 < x < 1.0 */ * (max - min) + 1);
        }
        samples.put(at, value);
      }

      writeToFile(outputDir.resolve(curDate + ".con"), samples);
      writeToScilabFile(outputDir.resolve(curDate + ".sci"), samples);
    }
  }
}
