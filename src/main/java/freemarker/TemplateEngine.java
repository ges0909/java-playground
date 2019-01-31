package freemarker;

import freemarker.template.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Optional;

public class TemplateEngine {
    private static Configuration configuration = new Configuration();

    public TemplateEngine() {
        configuration.setClassForTemplateLoading(getClass(), "/ftl");
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setIncompatibleImprovements(new Version(2, 3, 20));
    }

    public Optional<String> process(TemplateType mailTemplate, Object model, Locale locale) {
        try {
            Template template = configuration.getTemplate(mailTemplate.value(), locale);
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            return Optional.of(writer.toString());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
