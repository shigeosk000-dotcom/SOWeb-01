package com.aromatripnippon.repository;

import com.aromatripnippon.entity.AdminBackupCode;
import com.aromatripnippon.entity.AdminUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminBackupCodeRepository extends JpaRepository<AdminBackupCode, Long> {
  List<AdminBackupCode> findByAdminUserAndDeletedAtIsNullAndUsedAtIsNull(AdminUser adminUser);
  Optional<AdminBackupCode> findByAdminUserAndCodeHashAndDeletedAtIsNullAndUsedAtIsNull(AdminUser adminUser, String codeHash);
}
