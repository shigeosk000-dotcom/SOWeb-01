package com.aromatripnippon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table(name = "experience_programs")
public class ExperienceProgram extends BaseEntity {
  @NotBlank
  private String name;
  private String description;
  private Integer durationMinutes;
  private BigDecimal price;
  private String materialsSummary;
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
