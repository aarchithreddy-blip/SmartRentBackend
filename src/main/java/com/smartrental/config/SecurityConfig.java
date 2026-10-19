package com.smartrental.config;
import com.smartrental.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import java.util.Arrays;
@Configuration
public class SecurityConfig {
 @Value("${app.cors.allowed-origins}") private String allowedOrigins;
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwt) throws Exception {
  http.csrf(c->c.disable()).cors(c->c.configurationSource(corsConfigurationSource()))
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/properties","/api/properties/**","/api/health","/error").permitAll().anyRequest().authenticated())
   .addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
  return http.build();
 }
 @Bean CorsConfigurationSource corsConfigurationSource(){
  CorsConfiguration c=new CorsConfiguration();
  c.setAllowedOrigins(Arrays.stream(allowedOrigins.split(",")).map(String::trim).filter(s->!s.isBlank()).toList());
  c.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
  c.setAllowedHeaders(Arrays.asList("Authorization","Content-Type","Accept"));
  c.setAllowCredentials(false);
  UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();source.registerCorsConfiguration("/**",c);return source;
 }
}
