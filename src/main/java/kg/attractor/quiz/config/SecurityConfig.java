package kg.attractor.quiz.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String userQuery = "SELECT EMAIL, PASSWORD, ENABLED " +
                           "FROM USERS " +
                           "WHERE EMAIL = ?";

        String roleQuery = "SELECT u.EMAIL, r.ROLE_NAME " +
                           "FROM USERS u " +
                           "INNER JOIN ROLES r ON u.ROLE = r.ID " +
                           "WHERE u.EMAIL = ?";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery)
                .passwordEncoder(passwordEncoder);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  )
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest

                        .requestMatchers("/quizzes/**").hasAuthority("USER")
                        .requestMatchers("/users/api/users/*/statistics").hasAuthority("USER")

                        .requestMatchers(HttpMethod.POST, "/users/api/register").permitAll()

                        .anyRequest().authenticated()

                );

        return http.build();
    }
}
