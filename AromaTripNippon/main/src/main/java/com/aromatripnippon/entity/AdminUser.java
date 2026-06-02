package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_users")
public class AdminUser extends BaseEntity {
  @NotBlank
  @Size(max = 255)
  @Column(name = "login_id", nullable = false, unique = true)
  private String loginId;
  @NotBlank
  @Size(max = 255)
  @Column(name = "name", nullable = false)
  private String displayName;
  @Email
  @Size(max = 255)
  private String email;
  @NotBlank
  @Column(name = "password_hash", nullable = false)
  private String passwordHash;
  @NotBlank
  @Size(max = 255)
  private String role = "ADMIN";
  @Column(name = "is_active")
  private Boolean active = true;
  @Column(name = "notification_setting")
  @Size(max = 255)
  private String notificationSetting = "ON";
  @Column(name = "display_language")
  @Size(max = 255)
  private String displayLanguage = "Japanese";
  @Column(name = "failed_login_attempts", nullable = false)
  private Integer failedLoginAttempts = 0;
  @Column(name = "account_locked", nullable = false)
  private Boolean accountLocked = false;
  @Column(name = "password_reset_required", nullable = false)
  private Boolean passwordResetRequired = false;
  @Column(name = "password_reset_token_hash")
  private String passwordResetTokenHash;
  @Column(name = "password_reset_token_expires_at")
  private LocalDateTime passwordResetTokenExpiresAt;
  @Column(name = "totp_enabled", nullable = false)
  private Boolean totpEnabled = false;
  @Column(name = "totp_secret")
  private String totpSecret;
  @Column(name = "totp_pending_secret")
  private String totpPendingSecret;

  public String getLoginId() { return loginId; }
  public void setLoginId(String loginId) { this.loginId = loginId; }
  public String getDisplayName() { return displayName; }
  public void setDisplayName(String displayName) { this.displayName = displayName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }
  public Boolean getActive() { return active; }
  public void setActive(Boolean active) { this.active = active; }
  public String getNotificationSetting() { return notificationSetting; }
  public void setNotificationSetting(String notificationSetting) { this.notificationSetting = notificationSetting; }
  public String getDisplayLanguage() { return displayLanguage; }
  public void setDisplayLanguage(String displayLanguage) { this.displayLanguage = displayLanguage; }
  public Integer getFailedLoginAttempts() { return failedLoginAttempts; }
  public void setFailedLoginAttempts(Integer failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
  public Boolean getAccountLocked() { return accountLocked; }
  public void setAccountLocked(Boolean accountLocked) { this.accountLocked = accountLocked; }
  public Boolean getPasswordResetRequired() { return passwordResetRequired; }
  public void setPasswordResetRequired(Boolean passwordResetRequired) { this.passwordResetRequired = passwordResetRequired; }
  public String getPasswordResetTokenHash() { return passwordResetTokenHash; }
  public void setPasswordResetTokenHash(String passwordResetTokenHash) { this.passwordResetTokenHash = passwordResetTokenHash; }
  public LocalDateTime getPasswordResetTokenExpiresAt() { return passwordResetTokenExpiresAt; }
  public void setPasswordResetTokenExpiresAt(LocalDateTime passwordResetTokenExpiresAt) { this.passwordResetTokenExpiresAt = passwordResetTokenExpiresAt; }
  public Boolean getTotpEnabled() { return totpEnabled; }
  public void setTotpEnabled(Boolean totpEnabled) { this.totpEnabled = totpEnabled; }
  public String getTotpSecret() { return totpSecret; }
  public void setTotpSecret(String totpSecret) { this.totpSecret = totpSecret; }
  public String getTotpPendingSecret() { return totpPendingSecret; }
  public void setTotpPendingSecret(String totpPendingSecret) { this.totpPendingSecret = totpPendingSecret; }
}
