package com.example.atdd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desactivamos CSRF temporalmente
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll()  // Permitimos acceso a todas las rutas
            )
            .formLogin(form -> form.disable())  // Desactivamos el formulario de login de Spring Security
            .httpBasic(basic -> basic.disable());  // Desactivamos la autenticación básica

        return http.build();
    }
}