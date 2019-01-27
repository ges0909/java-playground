import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FileTests {

    @Test
    void streamFileContent() throws IOException {
        Path path = Paths.get("src/test/java/testdatei.txt").toAbsolutePath();
        List<String> list;
        try (Stream<String> stream = Files.lines(path)) {
            list = stream
                    .filter(line -> !line.startsWith("Zeile 2"))
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
        }
        assertThat(list.size()).isEqualTo(2);
    }
}
