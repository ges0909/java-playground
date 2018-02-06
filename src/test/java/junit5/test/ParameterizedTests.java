package junit5.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Shows the usage of a parameterized test.
 * 
 * A parameterized test is one where the @Test method is invoked multiple times
 * with different parameter values each time. A parameterized test must be
 * annotated with @ParameterizedTest, and must specify a source for its
 * arguments.
 * <ul>
 * <li>@ValueSource</li>
 * <li>@EnumSource</li>
 * <li>@MethodSource</li>
 * </ul>
 * 
 */
public class ParameterizedTests {

	class Person {
		String firstName;
		String lastName;

		Person(String firstName, String lastName) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
			result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			if (firstName == null) {
				if (other.firstName != null)
					return false;
			} else if (!firstName.equals(other.firstName))
				return false;
			if (lastName == null) {
				if (other.lastName != null)
					return false;
			} else if (!lastName.equals(other.lastName))
				return false;
			return true;
		}
	}

	@SuppressWarnings("unused")
	private static IntStream createIntStream() { // must be static
		return IntStream.rangeClosed(0, 20);
	}

	@SuppressWarnings("unused")
	private static Stream<Person> createPersonStream() {
		@SuppressWarnings("serial")
		List<Person> list = new ArrayList<Person>() {
			{
				add(new ParameterizedTests().new Person("Cold", "Play"));
				add(new ParameterizedTests().new Person("Neil", "Young"));
			}
		};
		return list.stream();
	}

	@DisplayName("@ValueSource with String's")
	@ParameterizedTest(name = "{index}: {0}")
	@ValueSource(strings = { "Hello", "JUnit", "5", "!" })
	void testValueSourceWithStrings(String word) {
		assertNotNull(word);
	}

	@ParameterizedTest
	@DisplayName("@ValueSource with Int's")
	@ValueSource(ints = { 1, 2 })
	void testValueSourceWithInts(int value) {
		assertNotNull(value);
		assertAll("", () -> assertTrue(value >= 1), () -> assertTrue(value <= 2));
	}

	@DisplayName("@ValueSource with Long's")
	@ParameterizedTest
	@ValueSource(longs = { 1L, 2L })
	void testValueSourceWithLongs(long value) {
		assertNotNull(value);
		assertAll("", () -> assertTrue(value >= 1), () -> assertTrue(value <= 2));
	}

	@DisplayName("@ValueSource with Double's")
	@ParameterizedTest
	@ValueSource(doubles = { 1.0, 2.0 })
	void testValueSourceWithDoubles(double value) {
		assertNotNull(value);
		assertAll("", () -> assertTrue(value >= 1), () -> assertTrue(value <= 2));
	}

	@DisplayName("@MethodSource with int stream")
	@ParameterizedTest
	@MethodSource("createIntStream")
	void testMethodSourceWithIntStream(int value) {
		assertNotNull(value);
		assertAll("", () -> assertTrue(value >= 0), () -> assertTrue(value <= 20));
	}

	@DisplayName("@MethodSource with object stream")
	@ParameterizedTest
	@MethodSource("createPersonStream")
	void testMethodSourceWithObjectStream(Person person) {
		assertNotNull(person);
		assertThat(person, anyOf(is(new Person("Neil", "Young")), is(new Person("Cold", "Play"))));
	}

	@DisplayName("@CsvSource")
	@ParameterizedTest(name = "{index}: {0} + {1} = {2}")
	@CsvSource({ "1, 1, 2", "2, 4, 6", "3, 9, 12" })
	void testCvsSource(int a, int b, int c) {
		assertTrue(a + b == c);
	}

	@DisplayName("@CsvFileSource")
	@ParameterizedTest(name = "{index}:  {0} + {1} = {2}")
	@CsvFileSource(resources = "/add.csv")
	void testCvsFileSource(int a, int b, int c) {
		assertTrue(a + b == c);
	}
}
