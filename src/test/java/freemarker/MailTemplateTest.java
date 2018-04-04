package freemarker;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import freemarker.template.TemplateException;

class MailTemplateTest {
	@Test
	void processTemplate() throws URISyntaxException, IOException, TemplateException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", "Hello World!");

		List<String> countries = new ArrayList<>();
		countries.add("India");
		countries.add("United States");
		countries.add("Germany");
		countries.add("France");
		model.put("countries", countries);

		MailTemplateEngine template = new MailTemplateEngine();
		Optional<String> result = template.process("helloworld.ftl", model, Locale.GERMAN);

		result.ifPresent(System.out::println);
	}
}
