import org.junit.jupiter.api.Test;
import resourcetest.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceTests {
    @Test
    void test_1() {
        String csvFileName = "add.csv";
        Resource resource = new Resource();
        String result = resource.getCSV(csvFileName);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
}
