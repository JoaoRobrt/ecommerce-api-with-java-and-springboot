package com.ecommerce.project.repositories;

import com.ecommerce.project.models.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);

    boolean existsByCategoryName(String categoryName);

    boolean existsByCategoryNameAndCategoryIdNot(String categoryName, Long categoryId);
}
