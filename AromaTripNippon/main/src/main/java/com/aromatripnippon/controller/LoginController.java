package com.aromatripnippon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  @GetMapping("/management/login")
  public String login() {
    return "management/login";
  }
}
