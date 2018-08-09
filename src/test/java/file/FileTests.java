package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class FileTests {

  @Test
  void stream() throws IOException {
    String filename = "./testdatei.txt";
    System.out.println(Paths.get(filename));
    try (Stream<String> stream = Files.lines(Paths.get(filename))) {
      stream.forEach(System.out::println);
      /* List<String> list = */ stream.filter(line -> !line.startsWith("Zeile 2")).map(String::toUpperCase)
          .collect(Collectors.toList());
    }
  }
}
