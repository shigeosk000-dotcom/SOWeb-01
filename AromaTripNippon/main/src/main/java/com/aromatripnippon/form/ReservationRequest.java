package com.aromatripnippon.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReservationRequest {
  @FutureOrPresent
  @NotNull
  private LocalDate visitDate;
  @NotBlank
  private String timeSlot;
  @Min(1)
  @Max(4)
  private Integer guestCount;
  @NotBlank
  private String name;
  @Email
  @NotBlank
  private String email;
  private String phone;
  private String nationality;
  private String preferredLanguage = "英語";
  private String requestNote;

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
}
