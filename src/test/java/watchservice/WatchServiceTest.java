package watchservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WatchServiceTest {

  WatchServiceDemo ws;

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