package com.aromatripnippon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;
  @ManyToOne(fetch = FetchType.LAZY)
  private ExperienceProgram experienceProgram;
  @FutureOrPresent
  @NotNull
  private LocalDate visitDate;
  @NotBlank
  private String timeSlot;
  @Min(1)
  @Max(4)
  private Integer guestCount;
  private String preferredLanguage = "English";
  private String requestNote;
  private String status = "RESERVED";

  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public ExperienceProgram getExperienceProgram() { return experienceProgram; }
  public void setExperienceProgram(ExperienceProgram experienceProgram) { this.experienceProgram = experienceProgram; }
  public LocalDate getVisitDate() { return visitDate; }
  public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }
  public String getTimeSlot() { return timeSlot; }
  public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
  public Integer getGuestCount() { return guestCount; }
  public void setGuestCount(Integer guestCount) { this.guestCount = guestCount; }
  public String getPreferredLanguage() { return preferredLanguage; }
  public void setPreferredLanguage(String preferredLanguage) { this.preferredLanguage = preferredLanguage; }
  public String getRequestNote() { return requestNote; }
  public void setRequestNote(String requestNote) { this.requestNote = requestNote; }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}
