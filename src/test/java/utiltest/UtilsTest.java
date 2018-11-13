package utiltest;

import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {
    @Test
    void convertCamelToUpperAndUnderscore() {
        assertThat("NONE").isEqualTo(Utils.camelToUpperAndUnderscore("none"));
        assertThat("CURRENT_YEAR").isEqualTo(Utils.camelToUpperAndUnderscore("currentYear"));
        assertThat("NEXT_YEAR").isEqualTo(Utils.camelToUpperAndUnderscore("nextYear"));
        assertThat("NEXT_MONTH").isEqualTo(Utils.camelToUpperAndUnderscore("nextMonth"));
        assertThat("NEXT_3_MONTH").isEqualTo(Utils.camelToUpperAndUnderscore("next3Months"));
        assertThat("NEXT_6_MONTH").isEqualTo(Utils.camelToUpperAndUnderscore("next6Months"));
        assertThat("NEXT_12_MONTHS").isEqualTo(Utils.camelToUpperAndUnderscore("next12Months"));
    }

    @Test
    void stringTemplate() {
        Object[] params = new Object[]{"Hello", "!"};
        String msg = MessageFormat.format("{0} world {1}", params);
        assertThat(msg).isEqualTo("Hello world !");
    }
}