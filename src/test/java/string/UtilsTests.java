package string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UtilsTests {
	@Test
	void tests() {
    assertEquals("NONE", Utils.camelToUpperAndUnderscore("none"));
    assertEquals("CURRENT_YEAR", Utils.camelToUpperAndUnderscore("currentYear"));
    assertEquals("NEXT_YEAR", Utils.camelToUpperAndUnderscore("nextYear"));
    assertEquals("NEXT_MONTH", Utils.camelToUpperAndUnderscore("nextMonth"));
    assertEquals("NEXT_3_MONTH", Utils.camelToUpperAndUnderscore("next3Months"));
    assertEquals("NEXT_6_MONTH", Utils.camelToUpperAndUnderscore("next6Months"));
    assertEquals("NEXT_12_MONTHNE", Utils.camelToUpperAndUnderscore("next12Months"));
	}
}