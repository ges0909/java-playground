package resourcetest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceTest {
    @Test
    void test_1() {
        String csvFileName = "gasMonth.csv";
        Resource resource = new Resource();
        String result = resource.getCSV(csvFileName);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
}
