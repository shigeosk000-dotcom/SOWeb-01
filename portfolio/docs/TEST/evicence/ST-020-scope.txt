package com.aromatripnippon;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class TemplateScopeTest {
  private static final List<String> FORBIDDEN_PHASE2_KEYS = List.of(
      "/orders",
      "/order-items",
      "/reviews",
      "/gallery",
      "/localization",
      "/mypage",
      "/cart",
      "user_accounts",
      "order_items",
      "gallery_posts",
      "localization_messages");

  @Test
  void templates_doNotContainPhase2LinksOrKeywords() throws IOException {
    Path templateRoot = Path.of("src", "main", "resources", "templates");
    try (var stream = Files.walk(templateRoot)) {
      List<Path> htmlFiles = stream.filter(path -> path.toString().endsWith(".html")).toList();
      for (Path file : htmlFiles) {
        String html = Files.readString(file);
        for (String key : FORBIDDEN_PHASE2_KEYS) {
          assertThat(html)
              .as("template %s should not contain key %s", file, key)
              .doesNotContain(key);
        }
      }
    }
  }
}
