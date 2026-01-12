package com.ecommerce.project.repositories;

import com.ecommerce.project.models.Category;
import com.ecommerce.project.models.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{

    Page<Product> findProductByCategory(Category category, Pageable pageable);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageable);

    boolean existsByProductName(String productName);

    boolean existsByProductNameAndProductIdNot(String productName, Long productId);
}
