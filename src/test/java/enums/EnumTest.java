package enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MediumTest {
	@Test
	void testEnergy() {
		Medium medium = Medium.ENERGY;
		assertEquals(0, medium.ordinal());
		assertEquals(9, medium.value());
		assertEquals("ENERGY", medium.name());
		assertEquals(Medium.ENERGY, Medium.find("ENERGY").get());
		assertEquals(Medium.ENERGY, Medium.find(9).get());
		assertTrue(Medium.isValid(9));
	}

	@Test
	void testGas() {
		Medium medium = Medium.GAS;
		assertEquals(1, medium.ordinal());
		assertEquals(99, medium.value());
		assertEquals("GAS", medium.name());
		assertEquals(Medium.GAS, Medium.find("GAS").get());
		assertEquals(Medium.GAS, Medium.find(99).get());
		assertTrue(Medium.isValid(99));
	}

	@Test
	void testWater() {
		Medium meium = Medium.WATER;
		assertEquals(2, meium.ordinal());
		assertEquals(999, meium.value());
		assertEquals("WATER", meium.name());
		assertEquals(Medium.WATER, Medium.find("WATER").get());
		assertEquals(Medium.WATER, Medium.find(999).get());
		assertTrue(Medium.isValid(999));
	}
}
