package com.aromatripnippon.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = ManagementController.class)
@Controller
public class ManagementExceptionHandler {
  @ExceptionHandler(NoSuchElementException.class)
  public String handleNoSuchElement(NoSuchElementException ex, HttpServletRequest request,
      RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("errorMessage",
        "対象データが見つかりませんでした。画面を更新して、一覧から再度操作してください。");
    return "redirect:" + resolveFallbackPath(request);
  }

  @ExceptionHandler(IllegalStateException.class)
  public String handleIllegalState(IllegalStateException ex, HttpServletRequest request,
      RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("errorMessage",
        "処理を完了できませんでした。入力内容を確認して、もう一度お試しください。");
    return "redirect:" + resolveFallbackPath(request);
  }

  private String resolveFallbackPath(HttpServletRequest request) {
    String referer = request.getHeader("Referer");
    if (referer == null || referer.isBlank()) {
      return "/management/dashboard";
    }
    int protocolIndex = referer.indexOf("://");
    if (protocolIndex < 0) {
      return "/management/dashboard";
    }
    int pathStart = referer.indexOf('/', protocolIndex + 3);
    if (pathStart < 0) {
      return "/management/dashboard";
    }
    String path = referer.substring(pathStart);
    if (!path.startsWith("/management") || path.startsWith("/management/login")) {
      return "/management/dashboard";
    }
    return path;
  }
}
