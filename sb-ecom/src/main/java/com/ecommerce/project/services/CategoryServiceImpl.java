package com.ecommerce.project.services;

import com.ecommerce.project.exceptions.ResourceNotFoudException;
import com.ecommerce.project.models.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl (CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    private Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoudException("Resource Not Found"));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }


    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public String delete(Long categoryId){
        Category foundedCategory = findById(categoryId);
        categoryRepository.delete(foundedCategory);
        return "Category with id: " + categoryId + " deleted successfully !";
    }

    @Override
    public Category update(Long categoryId,Category category) {
        Category categoryToUpdate = findById(categoryId);
        categoryToUpdate.setCategoryName(category.getCategoryName());
        return categoryRepository.save(categoryToUpdate);
    }
}
