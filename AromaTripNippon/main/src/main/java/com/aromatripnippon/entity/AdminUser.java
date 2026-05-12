package com.aromatripnippon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "admin_users")
public class AdminUser extends BaseEntity {
  @NotBlank
  private String loginId;
  @NotBlank
  private String displayName;
  @Email
  private String email;
  @NotBlank
  private String passwordHash;
  private String role = "ADMIN";
  private Boolean active = true;
  private String notificationSetting = "ON";
  private String displayLanguage = "日本語";

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
}
