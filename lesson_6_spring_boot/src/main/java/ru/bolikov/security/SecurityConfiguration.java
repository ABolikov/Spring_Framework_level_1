package ru.bolikov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.bolikov.repositories.UserRepository;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private void authConfigure(AuthenticationManagerBuilder auth,
                               UserDetailsService userDetailsService,
                               PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(provider);
//        auth.inMemoryAuthentication()
//                .withUser("test")
//                .password(passwordEncoder.encode("123"))
//                .roles("ADMIN");
    }

    @Bean
    public UserDetailsService userAuthService(UserRepository userRepository) {
        return new UserAuthService(userRepository);
    }

    @Configuration
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/").anonymous()
                    .antMatchers("/").permitAll()
                    .antMatchers("/admin", "/admin/**").hasAnyAuthority("ROLE_ADMIN","ROLE_ROOT")
                    .antMatchers("/admin/role/**").hasAnyAuthority("ROLE_ROOT")
                    .and()
                    .formLogin()
                    .usernameParameter("login")
                    .loginPage("/login")
                    .loginProcessingUrl("/authentificateTheUser")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/index");
        }
    }

   /* @Configuration
    @Order(1)
    public static class ApiSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/api/**").hasRole("ADMIN")
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint((req, resp, exception) -> {
                        resp.setContentType("application/json");
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.setCharacterEncoding("UTF-8");
                        resp.getWriter().println( "{ \"error\" : \"" + exception.getMessage()+ "\"}");
                    })
                    .and()
                    .csrf().disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }*/
}
