package com.ecommerce.project.services;

import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.exceptions.ResourceNotFoudException;
import com.ecommerce.project.models.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(Category category) {
        String categoryName = category.getCategoryName();
        checkCategoryNameUniqueness(categoryName);
        category.setCategoryName(categoryName);
        return categoryRepository.save(category);
    }

    @Override
    public String delete(Long categoryId){
        Category foundedCategory = findById(categoryId);
        categoryRepository.delete(foundedCategory);
        return "Category with id: " + categoryId + " deleted successfully.";
    }

    @Override
    public Category update(Long categoryId,Category category) {
        Category categoryToUpdate = findById(categoryId);

        String categoryName = category.getCategoryName();
        checkCategoryNameUniqueness(categoryId, categoryName);

        categoryToUpdate.setCategoryName(categoryName);
        return categoryRepository.save(categoryToUpdate);
    }

    //METODOS INTERNOS

    private Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoudException("Category Not Found."));
    }

    private void checkCategoryNameUniqueness(String categoryName){
        String normalized = normalize(categoryName);
        categoryRepository.findByCategoryNameIgnoreCase(normalized).ifPresent( c -> {
            throw new ResourceAlreadyExistsException("Category with name '" + normalized + "' already exists.");
        });
    }

    private void checkCategoryNameUniqueness(Long categoryId, String categoryName) {
        String normalized = normalize(categoryName);
        categoryRepository.findByCategoryNameIgnoreCase(normalized)
                .ifPresent(existing -> {
                    if (!existing.getCategoryId().equals(categoryId)) {
                        throw new ResourceAlreadyExistsException(
                                "Category with name '" + normalized + "' already exists."
                        );
                    }
                });
    }

    private String normalize(String categoryName) {
        return categoryName.trim().toLowerCase();
    }

}
