package com.aromatripnippon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "experience_programs")
public class ExperienceProgram extends BaseEntity {
  @NotBlank
  private String name;
  @Column(length = 1000)
  private String description;
  @NotNull
  @Positive
  @Column(name = "duration_minutes")
  private Integer durationMinutes;
  @NotNull
  @Positive
  private BigDecimal price;
  @Column(name = "material_summary", length = 1000)
  private String materialsSummary;
  @Column(name = "is_active")
  private Boolean active = true;

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public Integer getDurationMinutes() { return durationMinutes; }
  public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
  public BigDecimal getPrice() { return price; }
  public void setPrice(BigDecimal price) { this.price = price; }
  public String getMaterialsSummary() { return materialsSummary; }
  public void setMaterialsSummary(String materialsSummary) { this.materialsSummary = materialsSummary; }
  public Boolean getActive() { return active; }
  public void setActive(Boolean active) { this.active = active; }
}
