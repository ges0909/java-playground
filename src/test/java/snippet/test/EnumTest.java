package snippet.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import snippet.Enum;

class EnumsTest {
	@Test
	void test_1() {
		Enum media = Enum.ENERGY;
		assertTrue(media == Enum.ENERGY);
		assertEquals(0, media.ordinal());
		assertEquals("ENERGY", media.name());
		assertTrue(Enum.isValid(0));
	}

	@Test
	void test_2() {
		Enum media = Enum.GAS;
		assertEquals(0, media.ordinal());
		assertEquals("ENERGY", media.name());
		assertTrue(Enum.isValid(0));
	}
}
