package com.ecommerce.project.services;

import com.ecommerce.project.models.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> findAll() {
        return categories;
    }

    @Override
    public Category create(Category category) {
        categories.add(category);
        return category;
    }

    @Override
    public String delete(Long categoryId) {
        Category category = categories
                .stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst().orElse(null);
        if (category == null) return "Category with id: " + categoryId + " not found !";
        categories.remove(category);
        return "Category with id: " + categoryId + " deleted successfully !";
    }
}
