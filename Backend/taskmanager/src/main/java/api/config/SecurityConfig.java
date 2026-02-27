package api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .csrf(crsf -> crsf.disable()).authorizeHttpRequests(auhtz -> auhtz

         .requestMatchers("/api/auth/**").permitAll()
         .requestMatchers("/api/users/**").permitAll()
         .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .headers(headers -> headers
            .frameOptions(frame -> frame.disable())
        )
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable());
        return http.build();
    }
}
