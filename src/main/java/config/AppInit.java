package config;

import jakarta.servlet.ServletContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


// Класс AbstractAnnotationConfigDispatcherServletInitializer реализует интерфейс WebApplicationInitializer.
//WebApplicationInitializer – это интерфейс, предоставляемый Spring MVC,
// который позволяет гарантировать обнаружение реализации и её автоматическое использование
// для инициализации любого контейнера Servlet 3. Абстрактная реализация
// базового класса WebApplicationInitializer под названием AbstractDispatcherServletInitializer
// еще больше упрощает регистрацию DispatcherServlet, переопределяя методы для задания отображения
// сервлетов и местоположения конфигурации DispatcherServlet.

//Класс AbstractAnnotationConfigDispatcherServletInitializer - это замена конфигурационному файлу "web.xml"
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {


    // Метод, указывающий на классы конфигурации, т.е. просто их здесь перечисляем
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }


    //Добавление конфигурации, в которой инициализируем ViewResolver, для корректного отображения jsp. Имеется
    //в виду, что в классе WebConfig указаны значения prefix и suffix -
    //"templateResolver.setPrefix("/WEB-INF/pages/")" и "templateResolver.setSuffix(".html")"
    //Это замена файлу "applicationContext.xml".

    //Здесь нужно указать класс, который будет описывать наш ДиспетчерСервлет
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class []{
                WebConfig.class };
    }


    //Данный метод указывает url, на котором будет базироваться приложение.
    //Т.е. все запросы пользователя мы посылаем на диспетчерСервлет, т.е. слэш - "/"
    @Override
    protected String[] getServletMappings()  {
        return new String[]{"/"};
    }


    // Ниже два метода добавлены д/того, чтобы на стороне Спринга обрабатывалось скрытое hidden-поле "_method",
    // где находится реальный http-метод, который мы хотим использовать. В нашем случае - PATCH.
    //Это сделано д/того, чтобы корректно работал метод update в контроллере, у которого аннотация @PatchMapping ("/{id}")
    //Д/его корректной работы мы ниже создаем фильтр, который будет читать скрытое hidden-поле "_method",
    // значение которого будет PATCH
    //В Спринг Boot эти методы можно будет заменить одной строкой
    @Override
    public void onStartup(ServletContext aServletContext) throws jakarta.servlet.ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}