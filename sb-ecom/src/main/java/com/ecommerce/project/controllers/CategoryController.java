package com.ecommerce.project.controllers;

import com.ecommerce.project.models.Category;
import com.ecommerce.project.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;


    @GetMapping("/api/public/category")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @PostMapping("/api/admin/category")
    public Category createCategory(@RequestBody Category category) {
        categoryService.create(category);
        return category;
    }

    @DeleteMapping("/api/admin/category/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        return categoryService.delete(categoryId);
    }
}
