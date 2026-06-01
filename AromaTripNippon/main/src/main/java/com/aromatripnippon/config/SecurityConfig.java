package com.aromatripnippon.config;

import com.aromatripnippon.repository.AdminUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class SecurityConfig {
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http, AdminUserRepository admins) throws Exception {
    http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/concept", "/experience", "/reservation/**", "/css/**", "/js/**",
                "/assets/**", "/h2-console/**", "/management/login", "/management/forgot-password",
                "/management/reset-password").permitAll()
            .requestMatchers("/management/**").authenticated()
            .anyRequest().permitAll())
        .formLogin(login -> login
            .loginPage("/management/login")
            .loginProcessingUrl("/management/login")
            .defaultSuccessUrl("/management/dashboard", true)
            .successHandler(authenticationSuccessHandler(admins))
            .failureHandler(authenticationFailureHandler(admins))
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
            .accountLocked(Boolean.TRUE.equals(admin.getAccountLocked()) || Boolean.TRUE.equals(admin.getPasswordResetRequired()))
            .build())
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  @Bean
  AuthenticationSuccessHandler authenticationSuccessHandler(AdminUserRepository admins) {
    return new AuthenticationSuccessHandler() {
      private final RedirectStrategy redirect = new DefaultRedirectStrategy();
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
          org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(username).ifPresent(admin -> {
          admin.setFailedLoginAttempts(0);
          admins.save(admin);
        });
        redirect.sendRedirect(request, response, "/management/dashboard");
      }
    };
  }

  @Bean
  AuthenticationFailureHandler authenticationFailureHandler(AdminUserRepository admins) {
    return new AuthenticationFailureHandler() {
      private static final int LOCK_THRESHOLD = 5;
      private final RedirectStrategy redirect = new DefaultRedirectStrategy();
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
          org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        if (username != null && !username.isBlank()) {
          admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(username).ifPresent(admin -> {
            int current = admin.getFailedLoginAttempts() == null ? 0 : admin.getFailedLoginAttempts();
            int next = current + 1;
            admin.setFailedLoginAttempts(next);
            if (next >= LOCK_THRESHOLD) {
              admin.setAccountLocked(true);
              admin.setPasswordResetRequired(true);
            }
            admins.save(admin);
          });
        }
        String target = "/management/login?error";
        if (username != null && !username.isBlank()) {
          target = admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(username)
              .filter(admin -> Boolean.TRUE.equals(admin.getAccountLocked()) || Boolean.TRUE.equals(admin.getPasswordResetRequired()))
              .map(admin -> "/management/login?locked")
              .orElse("/management/login?error");
        }
        redirect.sendRedirect(request, response, target);
      }
    };
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

  @Bean
  ForwardedHeaderFilter forwardedHeaderFilter() {
    return new ForwardedHeaderFilter();
  }
}
