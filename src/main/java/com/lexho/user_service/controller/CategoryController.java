package com.lexho.user_service.controller;

import com.lexho.user_service.entity.ServiceCategory;
import com.lexho.user_service.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category") // 👈 यही important है
public class CategoryController {

    private final CategoryRepository repo;

    public CategoryController(CategoryRepository repo) {
        this.repo = repo;
    }

    // CREATE

    @PostMapping
    public ServiceCategory create(@RequestBody ServiceCategory category) {

        Optional<ServiceCategory> existing = repo.findByName(category.getName());

        if (existing.isPresent()) {
            throw new RuntimeException("Category already exists");
        }

        return repo.save(category);
    }

    // GET ALL
    @GetMapping
    public List<ServiceCategory> getAll() {
        return repo.findAll();
    }
}
