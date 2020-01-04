package web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import java.nio.charset.StandardCharsets;

public class ThymeleafUtil {

    private ThymeleafUtil() {
    }

    public static TemplateEngine getTemplateEngine(ServletContext context) {
        final ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(context);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
       // templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheTTLMs(1000L);
        final TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }
}
