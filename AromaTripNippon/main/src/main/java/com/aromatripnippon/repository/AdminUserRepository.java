package com.aromatripnippon.repository;

import com.aromatripnippon.entity.AdminUser;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
  Optional<AdminUser> findByIdAndDeletedAtIsNull(Long id);
  Optional<AdminUser> findByLoginIdAndDeletedAtIsNullAndActiveTrue(String loginId);
  Optional<AdminUser> findByLoginIdAndDeletedAtIsNull(String loginId);
  Optional<AdminUser> findByLoginIdAndEmailAndDeletedAtIsNull(String loginId, String email);
  Optional<AdminUser> findByPasswordResetTokenHashAndPasswordResetTokenExpiresAtAfterAndDeletedAtIsNull(
      String passwordResetTokenHash, LocalDateTime now);
}
