package com.lexho.user_service.controller;

import com.lexho.user_service.entity.SubCategory;
import com.lexho.user_service.repository.SubCategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryRepository repo;

    public SubCategoryController(SubCategoryRepository repo) {
        this.repo = repo;
    }

    // ✅ CREATE SUBCATEGORY
    @PostMapping
    public SubCategory create(@RequestBody SubCategory subCategory) {
        return repo.save(subCategory);
    }

    // ✅ GET ALL SUBCATEGORIES BY CATEGORY ID (BEST DESIGN 🔥)
    @GetMapping("/category/{id}")
    public List<SubCategory> getByCategory(@PathVariable Long id) {
        return repo.findByCategory_Id(id);
    }
}