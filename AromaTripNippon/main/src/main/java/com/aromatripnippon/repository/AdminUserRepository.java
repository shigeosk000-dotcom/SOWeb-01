package com.aromatripnippon.repository;

import com.aromatripnippon.entity.AdminUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
  Optional<AdminUser> findByLoginIdAndDeletedAtIsNullAndActiveTrue(String loginId);
}
