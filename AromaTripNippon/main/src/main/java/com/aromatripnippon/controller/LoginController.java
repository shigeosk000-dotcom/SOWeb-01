package com.aromatripnippon.controller;

import com.aromatripnippon.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
  private final PasswordResetService passwordResetService;

  public LoginController(PasswordResetService passwordResetService) {
    this.passwordResetService = passwordResetService;
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
  public String requestPasswordReset(@RequestParam String loginId, @RequestParam String email,
      RedirectAttributes redirectAttributes) {
    if (loginId != null && !loginId.isBlank() && email != null && !email.isBlank()) {
      passwordResetService.requestReset(loginId, email);
    }
    redirectAttributes.addFlashAttribute("infoMessage",
        "入力内容が正しければ、パスワード再設定用のメールを送信しました。");
    return "redirect:/management/forgot-password";
  }

  @GetMapping("/management/reset-password")
  public String resetPasswordForm(@RequestParam String token, Model model) {
    model.addAttribute("token", token);
    if (!passwordResetService.isTokenValid(token)) {
      model.addAttribute("errorMessage", "再設定リンクが無効か有効期限切れです。再度お試しください。");
    }
    return "management/reset-password";
  }

  @PostMapping("/management/reset-password")
  public String resetPassword(@RequestParam String token, @RequestParam String newPassword,
      @RequestParam String confirmPassword, Model model) {
    model.addAttribute("token", token);
    if (!passwordResetService.isTokenValid(token)) {
      model.addAttribute("errorMessage", "再設定リンクが無効か有効期限切れです。再度お試しください。");
      return "management/reset-password";
    }
    if (newPassword == null || newPassword.isBlank() || confirmPassword == null || confirmPassword.isBlank()) {
      model.addAttribute("errorMessage", "全ての項目を入力してください。");
      return "management/reset-password";
    }
    if (newPassword.length() < 8) {
      model.addAttribute("errorMessage", "新しいパスワードは8文字以上で入力してください。");
      return "management/reset-password";
    }
    if (!newPassword.equals(confirmPassword)) {
      model.addAttribute("errorMessage", "新しいパスワードと確認用パスワードが一致しません。");
      return "management/reset-password";
    }
    if (!passwordResetService.resetPassword(token, newPassword)) {
      model.addAttribute("errorMessage", "再設定リンクが無効か有効期限切れです。再度お試しください。");
      return "management/reset-password";
    }
    return "redirect:/management/login?reset";
  }
}
