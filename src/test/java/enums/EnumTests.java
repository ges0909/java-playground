package enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	void valueOfString() {
		assertEquals(Medium.ENERGY, Medium.valueOf("ENERGY"));
	}

	@Test
	void valueOfOrdinal() {
		assertEquals(Medium.ENERGY, Medium.values()[0]);
	}

	@Test
	@SuppressWarnings("unused")
	void arrayIndexOutOfBoundsException() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			Medium m = Medium.values()[9961];
		});
	}

	@Test
	void length() {
		assertEquals(5, Medium.values().length);
	}
}
