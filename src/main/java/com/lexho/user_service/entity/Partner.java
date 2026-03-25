package com.lexho.user_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "partner") // ✅ FIXED (IMPORTANT)
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long categoryId;
    private Long subCategoryId;
    private Double rating;
    private Double price;
    private Boolean available;
    private Integer experience;
    private String image;

    // GETTERS

    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getCategoryId() { return categoryId; }
    public Long getSubCategoryId() { return subCategoryId; }
    public Double getRating() { return rating; }
    public Double getPrice() { return price; }
    public Boolean getAvailable() { return available; }
    public Integer getExperience() { return experience; }
    public String getImage() { return image; }

    // SETTERS

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public void setSubCategoryId(Long subCategoryId) { this.subCategoryId = subCategoryId; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setPrice(Double price) { this.price = price; }
    public void setAvailable(Boolean available) { this.available = available; }
    public void setExperience(Integer experience) { this.experience = experience; }
    public void setImage(String image) { this.image = image; }
}