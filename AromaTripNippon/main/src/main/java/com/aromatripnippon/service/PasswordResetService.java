package com.aromatripnippon.service;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.repository.AdminUserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordResetService {
  private static final Logger log = LoggerFactory.getLogger(PasswordResetService.class);
  private final AdminUserRepository admins;
  private final JavaMailSender mailSender;
  private final PasswordEncoder encoder;
  private final String fromAddress;
  private final String appBaseUrl;

  public PasswordResetService(AdminUserRepository admins, JavaMailSender mailSender, PasswordEncoder encoder,
      @Value("${app.mail.from:no-reply@aromatripnippon.local}") String fromAddress,
      @Value("${app.base-url:http://localhost:8080}") String appBaseUrl) {
    this.admins = admins;
    this.mailSender = mailSender;
    this.encoder = encoder;
    this.fromAddress = fromAddress;
    this.appBaseUrl = appBaseUrl;
  }

  @Transactional
  public void requestReset(String loginId, String email) {
    admins.findByLoginIdAndEmailAndDeletedAtIsNull(loginId, email)
        .filter(admin -> Boolean.TRUE.equals(admin.getActive()))
        .ifPresent(admin -> {
          String token = generateToken();
          admin.setPasswordResetTokenHash(sha256(token));
          admin.setPasswordResetTokenExpiresAt(LocalDateTime.now().plusMinutes(15));
          admins.save(admin);
          try {
            sendResetMail(admin.getEmail(), token);
          } catch (MailException ex) {
            log.warn("Password reset mail send failed for loginId={}: {}", admin.getLoginId(), ex.getMessage());
          }
        });
  }

  public boolean isTokenValid(String token) {
    return findByToken(token).isPresent();
  }

  @Transactional
  public boolean resetPassword(String token, String newPassword) {
    Optional<AdminUser> adminOpt = findByToken(token);
    if (adminOpt.isEmpty()) {
      return false;
    }
    AdminUser admin = adminOpt.get();
    admin.setPasswordHash(encoder.encode(newPassword));
    admin.setFailedLoginAttempts(0);
    admin.setAccountLocked(false);
    admin.setPasswordResetRequired(false);
    admin.setPasswordResetTokenHash(null);
    admin.setPasswordResetTokenExpiresAt(null);
    admins.save(admin);
    return true;
  }

  private Optional<AdminUser> findByToken(String token) {
    return admins.findByPasswordResetTokenHashAndPasswordResetTokenExpiresAtAfterAndDeletedAtIsNull(
        sha256(token), LocalDateTime.now());
  }

  private void sendResetMail(String to, String token) {
    String resetUrl = appBaseUrl + "/management/reset-password?token=" + token;
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(fromAddress);
    message.setTo(to);
    message.setSubject("【AromaTripNippon】パスワード再設定のご案内");
    message.setText("以下のURLから15分以内にパスワードを再設定してください。\n" + resetUrl
        + "\n\nこのメールに心当たりがない場合は破棄してください。");
    mailSender.send(message);
  }

  private String generateToken() {
    byte[] random = new byte[32];
    new java.security.SecureRandom().nextBytes(random);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(random);
  }

  private String sha256(String value) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder(hash.length * 2);
      for (byte b : hash) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 algorithm is not available", e);
    }
  }
}
