package com.aromatripnippon.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fragrance_recipes")
public class FragranceRecipe extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  @NotNull
  private Customer customer;
  @NotBlank
  @Column(name = "recipe_name", nullable = false)
  private String recipeName;
  @Column(name = "concept_note", length = 1000)
  private String concept;
  @Column(name = "total_amount")
  private BigDecimal totalAmount = BigDecimal.ZERO;
  @Column(length = 1000)
  private String memo;
  @OneToMany(mappedBy = "fragranceRecipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FragranceRecipeMaterial> materials = new ArrayList<>();

  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public String getRecipeName() { return recipeName; }
  public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
  public String getConcept() { return concept; }
  public void setConcept(String concept) { this.concept = concept; }
  public BigDecimal getTotalAmount() { return totalAmount; }
  public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
  public String getMemo() { return memo; }
  public void setMemo(String memo) { this.memo = memo; }
  public List<FragranceRecipeMaterial> getMaterials() { return materials; }
  public void setMaterials(List<FragranceRecipeMaterial> materials) { this.materials = materials; }
}
