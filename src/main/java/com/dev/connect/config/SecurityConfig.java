package com.dev.connect.config;

import com.dev.connect.jwt.JwtAuthenticationEntryPoint;
import com.dev.connect.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(request->
               request.requestMatchers("/user/admin/**","/post/admin").hasRole("ADMIN")
                       .requestMatchers("/user/register").permitAll()
                       .requestMatchers("/auth/**").permitAll()
                       .anyRequest().authenticated()
        );

//        http.httpBasic(Customizer.withDefaults());
//        http.formLogin(Customizer.withDefaults());
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(ex->ex.authenticationEntryPoint(entryPoint));

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authentication) throws Exception {
        return authentication.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
