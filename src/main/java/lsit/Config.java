package lsit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import static org.springframework.security.config.Customizer.withDefaults;

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

                        .requestMatchers(HttpMethod.GET, "/electricians/{id}").hasAnyRole("ELECTRICIAN", "ADMIN")
                        // Instance of entities are created automatically, so there is no point in giving users
                        // control over creating additional instances
                        .requestMatchers(HttpMethod.POST, "/electricians/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/electricians/**").hasAnyRole("ELECTRICIAN", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/electricians/**").hasAnyRole("ELECTRICIAN", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/mechanics/{id}").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/mechanics/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/mechanics/**").hasAnyRole("MECHANIC", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/mechanics/**").hasAnyRole("MECHANIC", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/software-specialists/{id}").hasAnyRole("SOFTWARE-SPECIALIST", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/software-specialists/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/software-specialists/**").hasAnyRole("SOFTWARE-SPECIALIST", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/software-specialists/**").hasAnyRole("SOFTWARE-SPECIALIST", "ADMIN")

                        // All repair teams are able to manipulate reports
                        .requestMatchers("/repair-team-reports/**").hasAnyRole("ELECTRICIAN", "MECHANIC", "SOFTWARE-SPECIALIST", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/assemblers/{id}").hasAnyRole("ASSEMBLER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/assemblers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/assemblers/**").hasAnyRole("ASSEMBLER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/assemblers/**").hasAnyRole("ASSEMBLER", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/diagnosticians/{id}").hasAnyRole("DIAGNOSTICIAN", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/diagnosticians/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/diagnosticians/**").hasAnyRole("DIAGNOSTICIAN", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/diagnosticians/**").hasAnyRole("DIAGNOSTICIAN", "ADMIN")

                        // Diagnostic assessments are only written by diagnosticians
                        .requestMatchers("/diagnostic-assessments/**").hasAnyRole("DIAGNOSTICIAN", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/clients/{id}").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/clients/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/clients/**").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/clients/**").hasAnyRole("CLIENT", "ADMIN")

                        // Only clients can submit service requests
                        .requestMatchers("/service-requests/**").hasAnyRole("CLIENT", "ADMIN")

                        .requestMatchers(HttpMethod.GET, "/electricians").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mechanics").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/software-specialists").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/assemblers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/diagnosticians").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/clients").hasRole("ADMIN")

                        .anyRequest().permitAll()
                )
        ;

        return http.build();
    }

}