package mathtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PowerTest {

    private int roundUpToNextHighestPowerOfTwo(int value) {
        int highestBitOn = Integer.highestOneBit(value);
        return (value == highestBitOn) ? value : highestBitOn << 1;
    }

    @Test
    public void test0() {
        Assertions.assertEquals(0, roundUpToNextHighestPowerOfTwo(0));
    }

    @Test
    public void test2() {
        Assertions.assertEquals(2, roundUpToNextHighestPowerOfTwo(2));
    }

    @Test
    public void test3() {
        Assertions.assertEquals(4, roundUpToNextHighestPowerOfTwo(3));
    }

    @Test
    public void test255() {
        Assertions.assertEquals(256, roundUpToNextHighestPowerOfTwo(255));
    }

    @Test
    public void test256() {
        Assertions.assertEquals(256, roundUpToNextHighestPowerOfTwo(256));
    }

    @Test
    public void test257() {
        Assertions.assertEquals(512, roundUpToNextHighestPowerOfTwo(257));
    }

    @Test
    public void testNegative() {
        Assertions.assertEquals(0, roundUpToNextHighestPowerOfTwo(-1));
    }
}