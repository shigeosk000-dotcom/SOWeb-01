package com.aromatripnippon.service;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.entity.AuditLog;
import com.aromatripnippon.repository.AdminUserRepository;
import com.aromatripnippon.repository.AuditLogRepository;
import java.security.Principal;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
  private final AdminUserRepository admins;
  private final AuditLogRepository logs;

  public AuditService(AdminUserRepository admins, AuditLogRepository logs) {
    this.admins = admins;
    this.logs = logs;
  }

  public void record(Principal principal, String actionType, String targetTable, Long targetId, String detail) {
    AuditLog log = new AuditLog();
    if (principal != null) {
      admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(principal.getName()).ifPresent(log::setAdminUser);
    }
    log.setActionType(actionType);
    log.setTargetTable(targetTable);
    log.setTargetId(targetId);
    log.setDetail(detail);
    logs.save(log);
  }
}
