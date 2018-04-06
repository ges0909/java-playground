package freemarker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import freemarker.template.TemplateException;

class TemplateTest {
	static TemplateEngine template = new TemplateEngine();
	
	@ParameterizedTest
	@CsvSource({ 
		"subject, MPC9961-01,    , en",
		"body,    MPC9961-01,  on, en",
		"body,    MPC9961-01, off, en",
		"subject, MPC9961-01,    , de",
		"body,    MPC9961-01,  on, de",
		"body,    MPC9961-01, off, de"
	})
//	@Benchmark @BenchmarkMode(Mode.AverageTime)
	void templateTest(String scope, String name, String status, String locale) throws URISyntaxException, IOException, TemplateException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("scope", scope);
		model.put("name", name);
		model.put("status", status);
		model.put("date", LocalDate.now());
		model.put("time", LocalTime.now());
		Optional<String> result = template.process(TemplateType.MeteringPointCounter_Alarm, model, new Locale(locale));
		result.ifPresent(System.out::println);
	}
}