import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnumTests {

    @Test
    void ordinal() {
        Medium medium = Medium.ENERGY;
        assertEquals(0, medium.ordinal());
    }

    @Test
    void name() {
        Medium medium = Medium.GAS;
        assertEquals("GAS", medium.name());
    }

    @Test
    void valueOfString() {
        assertEquals(Medium.WATER, Medium.valueOf("WATER"));
    }

    @Test
    void valueOfOrdinal() {
        assertEquals(Medium.OIL, Medium.values()[3]);
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

    enum Medium {
        ENERGY, GAS, WATER, OIL, STEAM
    }
}
