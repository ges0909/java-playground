package junit5.test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AssumptionTests {
	@DisplayName("assumeTrue")
	@Test
	void testAssumeTrue() {
		String os = "windows";
		assumeTrue("linux".equals(os));
	}

	@DisplayName("assumeTrue")
	@Test
	void testAssumeTrue2() {
		String os = "linux";
		assumeTrue("linux".equals(os));
	}

	@DisplayName("assumingThat")
	@Test
	void testAssumingThat() {
		String s = null;
		String os = "windows";
		assumingThat("linux".equals(os), () -> s.length());
	}
}
