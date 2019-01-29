import org.junit.jupiter.api.Test;
import resource.ResourceDemo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceTests {
    @Test
    void test_1() {
        String csvFileName = "add.csv";
        ResourceDemo resource = new ResourceDemo();
        String result = resource.getCSV(csvFileName);
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
}
