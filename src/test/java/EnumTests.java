import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class EnumTests {

    @Test
    void ordinal() {
        Medium medium = Medium.ENERGY;
        assertThat(medium.ordinal()).isEqualTo(0);
    }

    @Test
    void name() {
        Medium medium = Medium.GAS;
        assertThat(medium.name()).isEqualTo("GAS");
    }

    @Test
    void valueOfWithStringArg() {
        assertThat(Medium.valueOf("WATER")).isEqualTo(Medium.WATER);
    }

    @Test
    void valueOfWithOrdinalArg() {
        assertThat(Medium.values()[3]).isEqualTo(Medium.OIL);
    }

    @Test
    void arrayIndexOutOfBoundsException() {
        Throwable thrown = catchThrowable(() -> {
            final Medium m = Medium.values()[9961];
        });
        assertThat(thrown).isInstanceOf(ArrayIndexOutOfBoundsException.class);
    }

    @Test
    void length() {
        assertThat(Medium.values().length).isEqualTo(5);
    }

    @Test
    void array() {
        Medium[] one = Medium.values();
        Medium[] two = Medium.values();
        assertThat(Arrays.deepEquals(one, two)).isTrue();
    }

    @Test
    void compare() {
        // since only one instance of the enum constant exists in JVM at a specific time,
        // we can use "==" without any problem; "==" is more preferable than "equals"
        // method as it provides run-time and compile-time safety to the comparison
        assertThat(Medium.STEAM == Medium.STEAM).isTrue();
        assertThat(Medium.STEAM == Medium.GAS).isFalse();
        assertThat(null == Medium.GAS).isFalse();
    }

    @Test
    void equals() {
        assertThat(Medium.STEAM.equals(Medium.STEAM)).isTrue();
        assertThat(Medium.STEAM.equals(Medium.GAS)).isFalse();
        Throwable thrown = catchThrowable(() -> {
            Medium gas = null;
            gas.equals(Medium.GAS);
        });
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void enumSet() {
        EnumSet<Medium> enumSet = EnumSet.of(Medium.GAS, Medium.STEAM);
        assertThat(enumSet)
            .contains(Medium.GAS, Medium.STEAM)
            .doesNotContain(Medium.ENERGY, Medium.WATER, Medium.OIL);
    }

    @Test
    void enumSetToStream() {
        EnumSet<Medium> enumSet = EnumSet.of(Medium.GAS, Medium.STEAM);
        List<Integer> ordinals = enumSet.stream().map(Medium::ordinal).collect(Collectors.toList());
        assertThat(Arrays.deepEquals(ordinals.toArray(), new Integer[]{1, 4})).isTrue();
    }

    @Test
    void enumMap() {
        EnumMap<Medium, String> enumMap = new EnumMap<>(Medium.class);
        enumMap.put(Medium.ENERGY, "Energie");
        enumMap.put(Medium.STEAM, "Dampf");
        assertThat(enumMap.values())
            .contains("Dampf", "Energie")
            .doesNotContain("Öl");
    }

    enum Medium {
        ENERGY, GAS, WATER, OIL, STEAM
    }
}
