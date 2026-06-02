package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_backup_codes")
public class AdminBackupCode extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "admin_user_id", nullable = false)
  private AdminUser adminUser;
  @Column(name = "code_hash", nullable = false)
  private String codeHash;
  @Column(name = "used_at")
  private LocalDateTime usedAt;

  public AdminUser getAdminUser() { return adminUser; }
  public void setAdminUser(AdminUser adminUser) { this.adminUser = adminUser; }
  public String getCodeHash() { return codeHash; }
  public void setCodeHash(String codeHash) { this.codeHash = codeHash; }
  public LocalDateTime getUsedAt() { return usedAt; }
  public void setUsedAt(LocalDateTime usedAt) { this.usedAt = usedAt; }
}
