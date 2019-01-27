import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import watchservicetest.WatchServiceDemo;

class WatchServiceTests {

    private WatchServiceDemo ws;

    @BeforeEach
    void setUp() {
        ws = new WatchServiceDemo();
    }

    @Test
    void testRun() throws InterruptedException {
        ws.run();
        Thread.sleep(10);
    }
}