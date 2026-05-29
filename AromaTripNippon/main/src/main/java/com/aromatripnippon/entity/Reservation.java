package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  @NotNull
  private Customer customer;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "experience_program_id", nullable = false)
  @NotNull
  private ExperienceProgram experienceProgram;
  @FutureOrPresent
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Column(name = "reservation_date", nullable = false)
  private LocalDate visitDate;
  @NotBlank
  @Column(name = "reservation_time", nullable = false)
  private String timeSlot;
  @Min(1)
  @Max(4)
  @NotNull
  @Column(name = "number_of_people", nullable = false)
  private Integer guestCount;
  @Column(name = "preferred_language")
  private String preferredLanguage = "English";
  @Column(name = "request_note", length = 1000)
  private String requestNote;
  @NotBlank
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
