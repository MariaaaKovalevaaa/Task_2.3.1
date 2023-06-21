package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

// Интерфейс WebMvcConfigurer реализуется тогда, когда мы под себя хотим реализовать Spring MVC.
// В данном случае, мы вместо стандартного шаблонизатора хотим использовать thymeleaf (таймлИф)

//Этот класс - замена файлу с описанием ДиспетчерСервлета. Теперь вся конфигурация ДиспетчерСервлета будет в этом классе.
@Configuration
@EnableWebMvc //Перевод - "делать возможным WebMvc", т.е. используется для включения Spring MVC в приложении
@ComponentScan("java")
public class WebConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    @Autowired // Внедряем applicationContext, как зависимость)
    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/view/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8"); //Добавляем, чтобы отображались кириллические символы на web-странице
        return templateResolver;
    }

    // Это настройка д/views. viewResolver - распознавать view
//    @Bean
//    public FreeMarkerViewResolver viewResolver () {
//        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
//        viewResolver.setSuffix(".html");
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setContentType("UTF-8");
//        viewResolver.setCache(false); //Не хотим кэшировать таймплэйты (шаблоны)
//        return viewResolver;
//    }


    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }


    // Здесь мы задаем шаблонизатор. В данном случае - thymeleaf (таймлИф)
    //Метод создаст бин типа ViewResolver который поможет DispatcherServlet определить
    // нужную JSP для отображения.

   @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");//Добавляем, чтобы отображались кириллические символы на web-странице
        resolver.setContentType("text/html; charset=UTF-8");//Добавляем, чтобы отображались кириллические символы на web-странице
        registry.viewResolver(resolver);
    }
}

