package web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


@Configuration
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext context) throws ServletException {
        super.onStartup(context);
        registerHiddenFilter(context);
    }

    public void registerHiddenFilter(ServletContext context) {
        context.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
                .addMappingForUrlPatterns(null, true, "/*");

        context.addFilter("characterEncodingFilter",
                        new CharacterEncodingFilter("UTF-8", true, true))
                .addMappingForUrlPatterns(null, false, "/*");
    }
}