package junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

public class RepeatTest {
	private static int repetitionCount = 0;

	/**
	 * Shows the configuration of test repetitions.
	 * 
	 * @param info
	 *            repetition info provided by JUnit runtime consisting of current
	 *            and total repetition counter
	 */
	@DisplayName("@RepeatedTest")
	@RepeatedTest(value = 3, name = "Repeated tests -- {currentRepetition}/{totalRepetitions}")
	void testRepeated(RepetitionInfo info) {
		repetitionCount += 1;
		assertEquals(repetitionCount, info.getCurrentRepetition());
	}
}
