import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PowerOfTwoTests {

    private int roundUpToNextHighestPowerOfTwo(int value) {
        int highestBitOn = Integer.highestOneBit(value);
        return (value == highestBitOn) ? value : highestBitOn << 1;
    }

    @Test
    void test0() {
        assertThat(roundUpToNextHighestPowerOfTwo(0)).isEqualTo(0);
    }

    @Test
    void test2() {
        assertThat(roundUpToNextHighestPowerOfTwo(2)).isEqualTo(2);
    }

    @Test
    void test3() {
        assertThat(roundUpToNextHighestPowerOfTwo(3)).isEqualTo(4);
    }

    @Test
    void test255() {
        assertThat(roundUpToNextHighestPowerOfTwo(255)).isEqualTo(256);
    }

    @Test
    void test256() {
        assertThat(roundUpToNextHighestPowerOfTwo(256)).isEqualTo(256);
    }

    @Test
    void test257() {
        assertThat(roundUpToNextHighestPowerOfTwo(257)).isEqualTo(512);
    }

    @Test
    void testNegative() {
        assertThat(roundUpToNextHighestPowerOfTwo(-1)).isEqualTo(0);
    }
}
