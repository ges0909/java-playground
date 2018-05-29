package datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTimeTest {

	final static LocalDate NOW = LocalDate.now();
	final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // don't use YYYY which is "week of
																																											// years"

	/*
	 * Manipulate LocalDateTime.
	 */
	@Test
	void addDayAndConvertLocalDateTime2Date() {
		LocalDateTime tomorrow = NOW.plusDays(1).atTime(12, 0, 0);
		/* Date d = */ Date.from(tomorrow.atZone(ZoneId.systemDefault()).toInstant()); // convert to 'Date'
	}

	@Test
	void subtractHoursAndMinutes() {
		LocalDateTime today = LocalDateTime.now();
		/* LocalDateTime later = */ today.minusHours(5).minusMinutes(30);
	}

	@Test
	void useTemporalAdjusters() {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, 22); // 22.02.2018
		// first day of february - 01.02.2018
		LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
		assertEquals(1, firstDayOfMonth.getDayOfMonth());
		assertTrue(Month.FEBRUARY == firstDayOfMonth.getMonth());
		assertEquals(2018, firstDayOfMonth.getYear());
		// last day of february - 28.02.2018
		LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
		assertEquals(28, lastDayOfMonth.getDayOfMonth());
		assertTrue(Month.FEBRUARY == lastDayOfMonth.getMonth());
		assertEquals(2018, lastDayOfMonth.getYear());
	}

	/*
	 * Parse String to LocalDate, LocalTime and LocalDateTime.
	 */

	@Test
	void parseDate() {
		String dateString = "19/02/2018";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dateString, formatter);
		assertEquals(19, date.getDayOfMonth());
		assertTrue(Month.FEBRUARY == date.getMonth());
		assertEquals(2018, date.getYear());
	}

	@Test
	void parseTime() {
		String timeString = "09:40:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = LocalTime.parse(timeString, formatter);
		assertEquals(9, time.getHour());
		assertEquals(40, time.getMinute());
		assertEquals(59, time.getSecond());
	}

	@Test
	void parseDateTime() {
		String dateString = "19/02/2018 09:40:59";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
		assertEquals(19, dateTime.getDayOfMonth());
		assertTrue(Month.FEBRUARY == dateTime.getMonth());
		assertEquals(2018, dateTime.getYear());
		assertEquals(9, dateTime.getHour());
		assertEquals(40, dateTime.getMinute());
		assertEquals(59, dateTime.getSecond());
	}

	@Test
	void isAfter() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		assertTrue(tomorrow.isAfter(today));
	}

	@Test
	void isBefore() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		assertTrue(today.isBefore(tomorrow));
	}

	@Test
	void isEqual() {
		LocalDate today = LocalDate.now();
		assertTrue(today.isEqual(today));
	}

	@Test
	void firstDayOfMonth() {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, 22);
		int dayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth()).getDayOfMonth();
		assertEquals(1, dayOfMonth);
	}

	@Test
	void lastDayOfMonth() {
		LocalDate date = LocalDate.of(2018, Month.FEBRUARY, 22);
		int dayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		assertEquals(28, dayOfMonth);
	}

	@Test
	void testStartAndEndOfDay() {
		LocalDateTime startOfDay = NOW.minusDays(1).atStartOfDay();
		LocalDateTime endOfDay = startOfDay.with(LocalTime.MAX);
		assertEquals("2018-05-28T00:00", startOfDay.toString());
		assertEquals("2018-05-28T23:59:59.999999999", endOfDay.toString());
	}

	/**
	 * Calculations with time periods.
	 */

	@DisplayName("1 // Current year")
	@Test
	void currentYear() {
		LocalDate startDate = NOW.withDayOfYear(1);
		LocalDate endDate = NOW;
		assertEquals("01/01/2018", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("2 // Last year")
	@Test
	void lastYear() {
		LocalDate startDate = NOW.minusYears(1).withDayOfYear(1);
		LocalDate endDate = NOW.minusYears(1).withDayOfYear(365);
		assertEquals("01/01/2017", startDate.format(FORMAT));
		assertEquals("31/12/2017", endDate.format(FORMAT));
	}

	@DisplayName("3 // Last 12 months")
	@Test
	void last12Months() {
		LocalDate startDate = NOW.minusMonths(12);
		LocalDate endDate = NOW;
		assertEquals("23/03/2017", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("4 // Last 3 years")
	@Test
	void last3Years() {
		LocalDate startDate = NOW.minusYears(3).withDayOfYear(1);
		LocalDate endDate = NOW.minusYears(1).withDayOfYear(365);
		assertEquals("01/01/2015", startDate.format(FORMAT));
		assertEquals("31/12/2017", endDate.format(FORMAT));
	}

	@DisplayName("5 // Current month")
	@Test
	void currentMonth() {
		LocalDate startDate = NOW.withDayOfMonth(1);
		LocalDate endDate = NOW;
		assertEquals("01/03/2018", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("6 // Last month")
	@Test
	void lastMonth() {
		LocalDate startDate = NOW.minusMonths(1).withDayOfMonth(1);
		LocalDate endDate = NOW.minusMonths(1).withDayOfMonth(28);
		assertEquals("01/02/2018", startDate.format(FORMAT));
		assertEquals("28/02/2018", endDate.format(FORMAT));
	}

	@DisplayName("7 // Last 15 days")
	@Test
	void last15Days() {
		LocalDate yesterday = NOW.minusDays(1);
		LocalDate startDate = yesterday.minusDays(15);
		LocalDate endDate = yesterday;
		assertEquals("28/02/2018", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("8 // Current + last year")
	@Test
	void currentAndLastYear() {
		LocalDate startDate = NOW.minusYears(1).withDayOfYear(1);
		LocalDate endDate = NOW;
		assertEquals("01/01/2017", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("9 // Current + last 11 months")
	@Test
	void currentAndLast11Months() {
		LocalDate startDate = NOW.minusMonths(11);
		LocalDate endDate = NOW;
		assertEquals("15/04/2017", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("10 // Current + last 3 years")
	@Test
	void currentAndLast3Years() {
		LocalDate startDate = NOW.minusYears(3).withDayOfYear(1);
		LocalDate endDate = NOW;
		assertEquals("01/01/2015", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("11 // Current + last month")
	@Test
	void currentAndLast() {
		LocalDate startDate = NOW.minusMonths(1).withDayOfMonth(1);
		LocalDate endDate = NOW;
		assertEquals("01/02/2018", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}

	@DisplayName("12 // Current + last 15 days")
	@Test
	void currentAndLAst15Days() {
		LocalDate startDate = NOW.minusDays(1L + 5);
		LocalDate endDate = NOW;
		assertEquals("28/02/2018", startDate.format(FORMAT));
		assertEquals("23/03/2018", endDate.format(FORMAT));
	}
}
