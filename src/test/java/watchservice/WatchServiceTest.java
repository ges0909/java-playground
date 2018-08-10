package watchservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WatchServiceTest {

  WatchService ws;

  @BeforeEach
  void setUp() {
    ws = new WatchService();
  }

  @Test
  void testRun() throws InterruptedException {
    ws.run();
    Thread.sleep(10);
  }
}