package com.aromatripnippon.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fragrance_recipes")
public class FragranceRecipe extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;
  @NotBlank
  private String recipeName;
  private String concept;
  private String memo;
  @OneToMany(mappedBy = "fragranceRecipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FragranceRecipeMaterial> materials = new ArrayList<>();

  public Customer getCustomer() { return customer; }
  public void setCustomer(Customer customer) { this.customer = customer; }
  public String getRecipeName() { return recipeName; }
  public void setRecipeName(String recipeName) { this.recipeName = recipeName; }
  public String getConcept() { return concept; }
  public void setConcept(String concept) { this.concept = concept; }
  public String getMemo() { return memo; }
  public void setMemo(String memo) { this.memo = memo; }
  public List<FragranceRecipeMaterial> getMaterials() { return materials; }
  public void setMaterials(List<FragranceRecipeMaterial> materials) { this.materials = materials; }
}
