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
                //.authorizeHttpRequests(auth -> auth
                   //     .requestMatchers("/api/auth/**", "/uploads/**").permitAll()
                     //   .anyRequest().authenticated()
               // )

.authorizeHttpRequests(auth -> auth
    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    .requestMatchers("/api/auth/**", "/uploads/**").permitAll()
    .anyRequest().authenticated()
)
                //  ADD THIS
                .addFilterBefore(jwtAuthFilter,
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    //React se connect karne ke liye

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration config = new CorsConfiguration();
//
//        //config.setAllowedOrigins(List.of("http://localhost:5173"));
//
//       // config.setAllowedOrigins(List.of(
//        //        "http://localhost:5173",
//        //        "https://lifereplay-frontend-x9fn.vercel.app"
//        //));
//
//           config.setAllowedOriginPatterns(List.of(
//           "http://localhost:5173",
//           "https://*.vercel.app"
//           ));
//
//
//        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowCredentials(false);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        //  Exact URL daalo wildcard ki jagah
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:3000",
                "https://lifereplay-frontend-i2i7-8ka7ibg12-md-sm-projects.vercel.app"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        //  Specific headers explicitly daalo
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
        ));

        // Response headers expose karo
        config.setExposedHeaders(List.of("Authorization"));

        config.setAllowCredentials(false);

        //  Preflight cache - baar baar OPTIONS na bheje browser
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
