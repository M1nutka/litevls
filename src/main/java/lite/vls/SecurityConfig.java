package lite.vls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lite.vls.users.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
            .csrf((csrf) -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/users/login", "/users/register").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());


        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(
        HttpSecurity http,
        UserService userService,
        PasswordEncoder passwordEncoder
    ) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}