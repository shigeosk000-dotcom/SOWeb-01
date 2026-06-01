package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
  @NotBlank
  @Size(max = 255)
  private String name;
  @Size(max = 255)
  private String nationality;
  @Email
  @NotBlank
  @Size(max = 255)
  private String email;
  @Size(max = 255)
  private String phone;
  @Column(name = "preferred_language")
  @Size(max = 255)
  private String preferredLanguage = "English";
  @Size(max = 255)
  private String purpose;
  @Column(name = "note", length = 1000)
  @Size(max = 1000)
  private String notes;

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
  public String getPurpose() { return purpose; }
  public void setPurpose(String purpose) { this.purpose = purpose; }
  public String getNotes() { return notes; }
  public void setNotes(String notes) { this.notes = notes; }
}
