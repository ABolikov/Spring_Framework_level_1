package ru.bolikov;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PersistConfig {

    @Value("${database.driver.class}")
    private String driverClassName;
    @Value("${database.user.name}")
    private String dbUrl;
    @Value("${database.password}")
    private String dbLogin;
    @Value("${database.url}")
    private String dbPassword;
    //подключение к БД тут будут имено наши данные
}
