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
                        .requestMatchers("/authentication").authenticated()
                        .requestMatchers("/clients").hasRole("CLIENT")
                        .requestMatchers("/electricians").hasRole("ELECTRICIAN")
                        .requestMatchers("/mechanics").hasRole("MECHANIC")
                        .requestMatchers("/software-specialists").hasRole("SOFTWARE-SPECIALIST")
                        .requestMatchers("/repair-team-reports").hasAnyRole("ELECTRICIAN", "MECHANIC", "SOFTWARE-SPECIALIST")
                        .requestMatchers("/diagnostician").hasRole("DIAGNOSTICIAN")
                        .requestMatchers("/diagnostic-assessments").hasRole("DIAGNOSTICIAN")
                        .requestMatchers("/assemblers").hasRole("ASSEMBLER")
                        .requestMatchers("/final-reports").hasRole("ASSEMBLER")
                        .anyRequest().permitAll()
                )
        ;

        return http.build();
    }

}