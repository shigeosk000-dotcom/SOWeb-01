package com.aromatripnippon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  private AdminUser adminUser;
  private String actionType;
  private String targetTable;
  private Long targetId;
  private String detail;

  public AdminUser getAdminUser() { return adminUser; }
  public void setAdminUser(AdminUser adminUser) { this.adminUser = adminUser; }
  public String getActionType() { return actionType; }
  public void setActionType(String actionType) { this.actionType = actionType; }
  public String getTargetTable() { return targetTable; }
  public void setTargetTable(String targetTable) { this.targetTable = targetTable; }
  public Long getTargetId() { return targetId; }
  public void setTargetId(Long targetId) { this.targetId = targetId; }
  public String getDetail() { return detail; }
  public void setDetail(String detail) { this.detail = detail; }
}
