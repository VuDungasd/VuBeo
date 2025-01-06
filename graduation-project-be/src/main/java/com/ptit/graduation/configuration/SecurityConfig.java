package com.ptit.graduation.configuration;

import com.ptit.graduation.service.security.JwtAuthenticationFilter;
import com.ptit.graduation.service.security.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtUtils jwtUtils;
  private final UserDetailsService userDetailsService;
  public SecurityConfig(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//          .csrf(AbstractHttpConfigurer::disable);
//    return http.build();
    http.csrf(csrf -> csrf.disable()) // Tắt CSRF nếu không cần thiết
          .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()
//                .requestMatchers("/auth/**").permitAll() // Cho phép các endpoint trong "/auth/**" truy cập không cần xác thực
//                .requestMatchers("/**").permitAll() // Cho phép các endpoint trong "/api/v1/products" truy cập không cần xác thực
//                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các endpoint khác
//                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các endpoint khác
          )
          .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Thêm bộ lọc JWT trước bộ lọc UsernamePasswordAuthentication

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
  }
}