package com.aromatripnippon.service;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.repository.AdminUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AdminUserService {
  private final AdminUserRepository admins;
  private final PasswordEncoder encoder;

  public AdminUserService(AdminUserRepository admins, PasswordEncoder encoder) {
    this.admins = admins;
    this.encoder = encoder;
  }

  public AdminUser findActive(Long id) {
    return admins.findByIdAndDeletedAtIsNull(id).orElseThrow();
  }

  @Transactional
  public AdminUser save(AdminUser admin, String rawPassword) {
    if (rawPassword != null && !rawPassword.isBlank()) {
      admin.setPasswordHash(encoder.encode(rawPassword));
    }
    return admins.save(admin);
  }

  @Transactional
  public void softDelete(Long id) {
    AdminUser admin = findActive(id);
    admin.softDelete();
    admin.setActive(false);
    admins.save(admin);
  }
}
