package com.aromatripnippon.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class ReservationRequest {
  @FutureOrPresent
  @NotNull
  private LocalDate visitDate;

  @NotBlank
  @Size(max = 255, message = "Time slot must be 255 characters or fewer.")
  private String timeSlot;

  @Min(1)
  @Max(4)
  private Integer guestCount;

  @NotBlank
  @Size(max = 255, message = "Name must be 255 characters or fewer.")
  private String name;

  @Email
  @NotBlank
  @Size(max = 255, message = "Email address must be 255 characters or fewer.")
  private String email;

  @Size(max = 255, message = "Phone number must be 255 characters or fewer.")
  private String phone;

  @Size(max = 255, message = "Nationality must be 255 characters or fewer.")
  private String nationality;

  @Size(max = 255, message = "Preferred language must be 255 characters or fewer.")
  private String preferredLanguage = "Japanese";

  @Size(max = 1000, message = "Requests or notes must be 1000 characters or fewer.")
  private String requestNote;

  @AssertTrue(message = "You must agree to the privacy policy and personal data handling.")
  private boolean privacyConsent;

  public LocalDate getVisitDate() { return visitDate; }
  public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }
  public String getTimeSlot() { return timeSlot; }
  public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
  public Integer getGuestCount() { return guestCount; }
  public void setGuestCount(Integer guestCount) { this.guestCount = guestCount; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }
  public String getNationality() { return nationality; }
  public void setNationality(String nationality) { this.nationality = nationality; }
  public String getPreferredLanguage() { return preferredLanguage; }
  public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }
  public String getRequestNote() { return requestNote; }
  public void setRequestNote(String requestNote) { this.requestNote = requestNote; }
  public boolean isPrivacyConsent() { return privacyConsent; }
  public void setPrivacyConsent(boolean privacyConsent) { this.privacyConsent = privacyConsent; }
}
