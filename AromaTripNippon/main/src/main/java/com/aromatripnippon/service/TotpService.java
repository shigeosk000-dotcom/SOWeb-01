package com.aromatripnippon.service;

import com.aromatripnippon.entity.AdminBackupCode;
import com.aromatripnippon.entity.AdminUser;
import com.aromatripnippon.repository.AdminBackupCodeRepository;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TotpService {
  private static final String TOTP_ALGO = "HmacSHA1";
  private static final int TIME_STEP_SECONDS = 30;
  private static final int WINDOW = 1;
  private static final int BACKUP_CODE_COUNT = 10;
  private final AdminBackupCodeRepository backupCodes;

  public TotpService(AdminBackupCodeRepository backupCodes) {
    this.backupCodes = backupCodes;
  }

  public String generateSecret() {
    byte[] random = new byte[20];
    new SecureRandom().nextBytes(random);
    return new Base32().encodeToString(random).replace("=", "");
  }

  public String buildOtpAuthUri(String appName, String accountName, String secret) {
    String issuer = urlEncode(appName);
    String label = issuer + ":" + urlEncode(accountName);
    return "otpauth://totp/" + label + "?secret=" + secret + "&issuer=" + issuer + "&algorithm=SHA1&digits=6&period=30";
  }

  public boolean verifyCode(String secret, String code) {
    if (secret == null || secret.isBlank() || code == null || !code.matches("\\d{6}")) {
      return false;
    }
    long nowStep = Instant.now().getEpochSecond() / TIME_STEP_SECONDS;
    for (int i = -WINDOW; i <= WINDOW; i++) {
      String expected = generateCode(secret, nowStep + i);
      if (expected.equals(code)) {
        return true;
      }
    }
    return false;
  }

  @Transactional
  public List<String> regenerateBackupCodes(AdminUser admin) {
    List<AdminBackupCode> active = backupCodes.findByAdminUserAndDeletedAtIsNullAndUsedAtIsNull(admin);
    for (AdminBackupCode code : active) {
      code.softDelete();
    }
    if (!active.isEmpty()) {
      backupCodes.saveAll(active);
    }

    List<String> plainCodes = new ArrayList<>();
    List<AdminBackupCode> entities = new ArrayList<>();
    for (int i = 0; i < BACKUP_CODE_COUNT; i++) {
      String plain = generateBackupCode();
      plainCodes.add(plain);
      AdminBackupCode entity = new AdminBackupCode();
      entity.setAdminUser(admin);
      entity.setCodeHash(sha256(plain));
      entities.add(entity);
    }
    backupCodes.saveAll(entities);
    return plainCodes;
  }

  @Transactional
  public boolean consumeBackupCode(AdminUser admin, String plainCode) {
    if (plainCode == null || plainCode.isBlank()) {
      return false;
    }
    return backupCodes.findByAdminUserAndCodeHashAndDeletedAtIsNullAndUsedAtIsNull(admin, sha256(plainCode.trim()))
        .map(code -> {
          code.setUsedAt(LocalDateTime.now(ZoneOffset.UTC));
          backupCodes.save(code);
          return true;
        }).orElse(false);
  }

  private String generateCode(String secret, long counter) {
    try {
      byte[] secretBytes = new Base32().decode(secret);
      byte[] data = ByteBuffer.allocate(8).putLong(counter).array();
      Mac mac = Mac.getInstance(TOTP_ALGO);
      mac.init(new SecretKeySpec(secretBytes, TOTP_ALGO));
      byte[] hash = mac.doFinal(data);
      int offset = hash[hash.length - 1] & 0x0F;
      int binary = ((hash[offset] & 0x7F) << 24)
          | ((hash[offset + 1] & 0xFF) << 16)
          | ((hash[offset + 2] & 0xFF) << 8)
          | (hash[offset + 3] & 0xFF);
      int otp = binary % 1_000_000;
      return String.format("%06d", otp);
    } catch (Exception ex) {
      return "000000";
    }
  }

  private String generateBackupCode() {
    byte[] random = new byte[6];
    new SecureRandom().nextBytes(random);
    return new Base32().encodeToString(random).replace("=", "").substring(0, 10);
  }

  private String sha256(String value) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder(hash.length * 2);
      for (byte b : hash) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  private String urlEncode(String value) {
    return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
  }
}
