import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BitwiseOperatorTests {

    @Test
    void bitwiseOR() {
        assertThat(0b0110 | 0b0101).isEqualTo(0b0111);
    }

    @Test
    void bitwiseAND() {
        assertThat(0b0110 & 0b0101).isEqualTo(0b0100);
    }

    @Test
    void bitwiseXOR() {
        assertThat(0b0110 ^ 0b0101).isEqualTo(0b0011); // gives 1 if both bits are different
    }

    @Test
    void bitwiseComplement() {
        assertThat(~0b00000000_00000000_00000000_00000110).isEqualTo(0b11111111_11111111_11111111_11111001);
    }

    @Test
    void bitwiseSignedLeftShift() {
        assertThat(0b00000000_00000000_00000000_00000110 << 1).isEqualTo(0b00000000_00000000_00000000_00001100);
        assertThat(0b00000000_00000000_00000000_00000110 << 2).isEqualTo(0b00000000_00000000_00000000_00011000);
        assertThat(0b00000000_00000000_00000000_00000110 << 3).isEqualTo(0b00000000_00000000_00000000_00110000);
    }

    @Test
    void bitwiseSignedRightShift() {
        assertThat(0b00000000_00000000_00000000_00000110 >> 1).isEqualTo(0b00000000_00000000_00000000_00000011);
        assertThat(0b00000000_00000000_00000000_00000110 >> 2).isEqualTo(0b00000000_00000000_00000000_00000001);
        assertThat(0b00000000_00000000_00000000_00000110 >> 3).isEqualTo(0b00000000_00000000_00000000_00000000);
        assertThat(0b10000000_00000000_00000000_00000110 >> 1).isEqualTo(0b11000000_00000000_00000000_00000011);
        assertThat(0b10000000_00000000_00000000_00000110 >> 2).isEqualTo(0b11100000_00000000_00000000_00000001);
        assertThat(0b10000000_00000000_00000000_00000110 >> 3).isEqualTo(0b11110000_00000000_00000000_00000000);
    }

    @Test
    void bitwiseUnsignedRightShift() {
        assertThat(0b00000000_00000000_00000000_00000110 >>> 1).isEqualTo(0b00000000_00000000_00000000_00000011);
        assertThat(0b00000000_00000000_00000000_00000110 >>> 2).isEqualTo(0b00000000_00000000_00000000_00000001);
        assertThat(0b00000000_00000000_00000000_00000110 >>> 3).isEqualTo(0b00000000_00000000_00000000_00000000);
        assertThat(0b10000000_00000000_00000000_00000110 >>> 1).isEqualTo(0b01000000_00000000_00000000_00000011);
        assertThat(0b10000000_00000000_00000000_00000110 >>> 2).isEqualTo(0b00100000_00000000_00000000_00000001);
        assertThat(0b10000000_00000000_00000000_00000110 >>> 3).isEqualTo(0b00010000_00000000_00000000_00000000);
    }
}
