package com.aromatripnippon.controller;

import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.repository.AdminUserRepository;
import com.aromatripnippon.service.TotpService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
  private final AdminUserRepository admins;
  private final TotpService totpService;
  private final PasswordEncoder encoder;

  public LoginController(AdminUserRepository admins, TotpService totpService, PasswordEncoder encoder) {
    this.admins = admins;
    this.totpService = totpService;
    this.encoder = encoder;
  }

  @GetMapping("/management/login")
  public String login() {
    return "management/login";
  }

  @GetMapping("/management/forgot-password")
  public String forgotPassword() {
    return "management/forgot-password";
  }

  @PostMapping("/management/forgot-password")
  public String forgotPasswordSubmit(@RequestParam String loginId,
      @RequestParam String newPassword,
      @RequestParam String confirmPassword,
      @RequestParam(required = false) String totpCode,
      @RequestParam(required = false) String backupCode,
      Model model,
      RedirectAttributes redirectAttributes) {
    if (newPassword == null || newPassword.length() < 8) {
      model.addAttribute("errorMessage", "新しいパスワードは8文字以上で入力してください。");
      return "management/forgot-password";
    }
    if (!newPassword.equals(confirmPassword)) {
      model.addAttribute("errorMessage", "新しいパスワード（確認）が一致しません。");
      return "management/forgot-password";
    }

    AdminUser admin = admins.findByLoginIdAndDeletedAtIsNullAndActiveTrue(loginId).orElse(null);
    if (admin == null || !Boolean.TRUE.equals(admin.getTotpEnabled()) || admin.getTotpSecret() == null) {
      model.addAttribute("errorMessage", "入力内容が正しくありません。");
      return "management/forgot-password";
    }

    boolean verified = totpService.verifyCode(admin.getTotpSecret(), trimToNull(totpCode))
        || totpService.consumeBackupCode(admin, trimToNull(backupCode));
    if (!verified) {
      model.addAttribute("errorMessage", "認証コードまたはバックアップコードが正しくありません。");
      return "management/forgot-password";
    }

    admin.setPasswordHash(encoder.encode(newPassword));
    admin.setFailedLoginAttempts(0);
    admin.setAccountLocked(false);
    admin.setPasswordResetRequired(false);
    admin.setPasswordResetTokenHash(null);
    admin.setPasswordResetTokenExpiresAt(null);
    admins.save(admin);
    redirectAttributes.addFlashAttribute("successMessage", "パスワードを再設定しました。");
    return "redirect:/management/login?reset";
  }

  private String trimToNull(String value) {
    if (value == null) {
      return null;
    }
    String trimmed = value.trim();
    return trimmed.isEmpty() ? null : trimmed;
  }
}
