package optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class OptionalTests {
  @Test
  void test() {
    String value = null;
    Optional<String> empty = Optional.empty();
    Optional<String> name = Optional.of("Escobar");
    String name2 = Optional.ofNullable(value).orElseGet(String::new);
    empty.ifPresent(System.out::println);
    name.ifPresent(System.out::println);
    assertEquals("", name2);
  }
}