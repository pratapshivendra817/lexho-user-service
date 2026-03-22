package com.lexho.user_service.dto;

public class PartnerResponseDTO {

    private Long id;
    private String name;
    private Double rating;
    private Double price;
    private Integer experience;
    private String image;
    private Long categoryId;
    private Long subCategoryId;

    // ✅ Constructor
    public PartnerResponseDTO(Long id, String name, Double rating, Double price,
                              Integer experience, String image,
                              Long categoryId, Long subCategoryId) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.experience = experience;
        this.image = image;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
    }

    // ✅ Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getRating() {
        return rating;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getExperience() {
        return experience;
    }

    public String getImage() {
        return image;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }
}