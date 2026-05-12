package com.aromatripnippon.config;

import com.aromatripnippon.repository.AdminUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/concept", "/experience", "/reservation/**", "/css/**", "/js/**",
                "/assets/**", "/h2-console/**").permitAll()
            .requestMatchers("/management/**").authenticated()
            .anyRequest().permitAll())
        .formLogin(login -> login
            .loginPage("/management/login")
            .loginProcessingUrl("/management/login")
            .defaultSuccessUrl("/management/dashboard", true)
            .failureUrl("/management/login?error")
            .permitAll())
        .logout(logout -> logout
            .logoutUrl("/management/logout")
            .logoutSuccessUrl("/management/login?logout"))
        .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
    return http.build();
  }

  @Bean
  UserDetailsService userDetailsService(AdminUserRepository admins) {
    return username -> admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(username)
        .map(admin -> org.springframework.security.core.userdetails.User
            .withUsername(admin.getLoginId())
            .password(admin.getPasswordHash())
            .roles(admin.getRole())
            .build())
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  @Bean
  AuthenticationManager authenticationManager(UserDetailsService users, PasswordEncoder encoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(users);
    provider.setPasswordEncoder(encoder);
    return new ProviderManager(provider);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
