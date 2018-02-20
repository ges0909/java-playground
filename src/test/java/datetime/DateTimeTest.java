package datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateTimeTest {
	@Test
	void addTimePeriod() {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime tomorrow = today.plusDays(1);
		/*Date d =*/ Date.from(tomorrow.atZone(ZoneId.systemDefault()).toInstant()); // convert to 'Date'
	}

	@Test
	void parseDateString() {
		String dateString = "19/02/2018";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dateString, formatter);
		assertEquals(19, date.getDayOfMonth());
		assertEquals(Month.FEBRUARY, date.getMonth());
		assertEquals(2018, date.getYear());
	}

	@Test
	void parseTimeString() {
		String timeString = "09:40:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = LocalTime.parse(timeString, formatter);
		assertEquals(9, time.getHour());
		assertEquals(40, time.getMinute());
		assertEquals(59, time.getSecond());
	}

	@Test
	void parseDateTimeString() {
		String dateString = "19/02/2018 09:40:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
		assertEquals(19, dateTime.getDayOfMonth());
		assertEquals(Month.FEBRUARY, dateTime.getMonth());
		assertEquals(2018, dateTime.getYear());
		assertEquals(9, dateTime.getHour());
		assertEquals(40, dateTime.getMinute());
		assertEquals(59, dateTime.getSecond());
	}
}
