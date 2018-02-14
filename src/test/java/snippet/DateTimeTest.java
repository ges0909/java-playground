package snippet;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateTimeTest {
	@Test
	void test_1() {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime tomorrow = today.plusDays(1);
		/*Date d =*/ Date.from(tomorrow.atZone(ZoneId.systemDefault()).toInstant());
	}
}
