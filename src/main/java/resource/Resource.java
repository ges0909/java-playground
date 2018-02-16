package resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Resource {
	public String getCSV(String csvFileName) {
		StringBuilder data = new StringBuilder();
		try {
			Path path = Paths.get(getClass().getClassLoader().getResource(csvFileName).toURI());
			try (Stream<String> lines = Files.lines(path)) {
				lines.forEach(line -> data.append(line).append("\n"));
			}
		} catch (URISyntaxException | IOException e) {
			return "";
		}
		return data.toString();
	}
}