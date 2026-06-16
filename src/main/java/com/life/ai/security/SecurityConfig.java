package com.life.ai.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthFilter jwtAuthFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/uploads/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 🔥 ADD THIS
                .addFilterBefore(jwtAuthFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    //React se connect karne ke liye

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

//        config.setAllowedOrigins(List.of("http://localhost:5173"));

       // config.setAllowedOrigins(List.of(
        //        "http://localhost:5173",
        //        "https://lifereplay-frontend-x9fn.vercel.app"
        //));

config.setAllowedOriginPatterns(List.of(
    "http://localhost:5173",
    "https://*.vercel.app"
));


        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
