package ru.bolikov;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ru.bolikov.repositories")
@PropertySource("classpath:application.properties")
public class PersistConfig {

    @Value("${database.driver.class}")
    private String driverClassName;

    @Value("${database.url}")
    private String dbUrl;

    @Value("${database.user.name}")
    private String dbLogin;

    @Value("${database.password}")
    private String dbPassword;

    @Bean("dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUsername(dbLogin);
        ds.setPassword(dbPassword);
        ds.setUrl(dbUrl);
        return ds;
    }

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManager() {
        // Создаем класса фабрики, реализующей интерфейс
        // FactoryBean<EntityManagerFactory>
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        // Задаем источник подключения
        factory.setDataSource(dataSource());
        // Задаем адаптер для конкретной реализации JPA,
        // указывает, какая именно библиотека будет использоваться в качестве
        // поставщика постоянства
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // Указание пакета, в котором будут находиться классы-сущности
        factory.setPackagesToScan("ru.bolikov.entity");
        // Подключаем настройки Hibernate
        factory.setJpaProperties(jpaProperties());
        return factory;
    }

    @Bean(name="jpaProperties")
    public Properties jpaProperties() {
        // Создание свойств для настройки Hibernate
        Properties jpaProperties = new Properties();
        // Создание таблиц на основании связей и описанния сущностей
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        // Указание диалекта конкретной базы данных
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        // Указание максимальной глубины связи
        jpaProperties.put("hibernate.max_fetch_depth", 3);
        // Максимальное количество строк, возвращаемых за один запрос из БД
        jpaProperties.put("hibernate.jdbc.fetch_size", 50);
        // Максимальное количество запросов при использовании пакетных операций
        jpaProperties.put("hibernate.jdbc.batch_size", 10);
        // Включает логирование
        jpaProperties.put("hibernate.show_sql", true);
        // Формтирование запросов в логах
        jpaProperties.put("hibernate.format_sql", true);
        return jpaProperties;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        // Создание менеджера транзакций
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory);
        return tm;
    }
}
