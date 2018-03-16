package enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EnumTests {
	enum Medium {
		ENERGY, GAS, WATER, OIL, STEAM;
	};

	@Test
	void ordinal() {
		Medium medium = Medium.ENERGY;
		assertEquals(0, medium.ordinal());
	}

	@Test
	void name() {
		Medium medium = Medium.ENERGY;
		assertEquals("ENERGY", medium.name());
	}

	@Test
	void valueOf() {
		assertEquals(Medium.ENERGY, Medium.valueOf("ENERGY"));
	}
}
