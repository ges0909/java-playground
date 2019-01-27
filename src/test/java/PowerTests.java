import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PowerTests {

    private int roundUpToNextHighestPowerOfTwo(int value) {
        int highestBitOn = Integer.highestOneBit(value);
        return (value == highestBitOn) ? value : highestBitOn << 1;
    }

    @Test
    void test0() {
        Assertions.assertEquals(0, roundUpToNextHighestPowerOfTwo(0));
    }

    @Test
    void test2() {
        Assertions.assertEquals(2, roundUpToNextHighestPowerOfTwo(2));
    }

    @Test
    void test3() {
        Assertions.assertEquals(4, roundUpToNextHighestPowerOfTwo(3));
    }

    @Test
    void test255() {
        Assertions.assertEquals(256, roundUpToNextHighestPowerOfTwo(255));
    }

    @Test
    void test256() {
        Assertions.assertEquals(256, roundUpToNextHighestPowerOfTwo(256));
    }

    @Test
    void test257() {
        Assertions.assertEquals(512, roundUpToNextHighestPowerOfTwo(257));
    }

    @Test
    void testNegative() {
        Assertions.assertEquals(0, roundUpToNextHighestPowerOfTwo(-1));
    }
}