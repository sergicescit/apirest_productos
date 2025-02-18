package com.fullstack1.backend.security.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())   //Desabilita csfr dando acceso a Postman y Front
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.PUT, "/usuarios/**").permitAll() 
                .requestMatchers("/usuarios/**").permitAll()  //damos acceso a las rutas
                .anyRequest().authenticated()   //Protege las demás rutas
            );

            return http.build();
    }

    @Bean
    public UserDetailsService UserDetailService() {
        UserDetails user = User.builder()
            .username("usuario")
            .password(passwordEncoder().encode("1234"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
