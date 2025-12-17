package com.ecommerce.project.services;

import com.ecommerce.project.models.Category;

import java.util.List;


public interface CategoryService {
    List<Category> findAll();
    Category create(Category category);
    String delete(Long id);
}
