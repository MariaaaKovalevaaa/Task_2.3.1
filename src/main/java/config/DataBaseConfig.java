package config;

import com.mchange.v2.c3p0.DriverManagerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement //означает, что классы, помеченные @Transactional, должны быть обернуты аспектом транзакций.
@ComponentScan("java")
public class DataBaseConfig {

    //Нужен д/работы с БД. Чтобы его проинициализировать, ниже создавали DataSource.
    //EntityManager дает возможность автоматического создания бинов и создания таблиц на основе Entity.
    //Чтобы получить эти возможности мы инициализируем этот Спринговый элемент - EntityManager
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource()); //Здесь устанавливаем DataSource, который ниже прописали
        em.setPackagesToScan("src.main.java.model");//Указываем, где находится тот Entity-класс, которым EntityManager будет управлять
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); //Здесь мы указываем, что в качестве JPA-провайдера мы будем использовать hibernate
        em.setJpaProperties(getHibernateProperties()); //Ниже эти проперти. Здесь устанавливаем их EntityManager'у
        return em;
    }

   @Bean
   public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/spring_jpa");
        dataSource.setUser("rootroot");
        dataSource.setPassword("rootroot");
        return dataSource;
    }

    //Эта настройка нужна д/поддержки транзакций
   @Bean
   public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }


    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }


    //Метод, который возвращает hibernate-проперти, которые устанавливаются EntityManager'у в LocalContainerEntityManagerFactoryBean
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create"); //Св-во, определяющее, что делать с таблицей
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return properties;
    }
}

