package lsit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Config{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .oauth2Login(withDefaults())
                .authorizeHttpRequests(a -> a
                        .requestMatchers(HttpMethod.GET, "/home").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/pets").hasRole("PETS")
                        .anyRequest().permitAll()
                )
        ;

        return http.build();
    }

}