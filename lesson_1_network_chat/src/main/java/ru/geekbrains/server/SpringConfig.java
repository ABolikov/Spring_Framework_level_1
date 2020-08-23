package ru.geekbrains.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.geekbrains.server.auth.AuthServiceJdbcImpl;
import ru.geekbrains.server.persistance.UserRepository;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class SpringConfig {

    @Bean("dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("actc002f");
        ds.setUrl("jdbc:mysql://localhost:3306/network_chat?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        return ds;
    }

    @Bean("userRepository")
    public UserRepository userRepository(DataSource dataSource) throws SQLException {
        return new UserRepository(dataSource);
    }

    @Bean("authService")
    public AuthServiceJdbcImpl authService(UserRepository userRepository){
        return new AuthServiceJdbcImpl(userRepository);
    }

    @Bean("server")
    public Server server(AuthServiceJdbcImpl authService) {
        return new Server(authService);
    }
}
