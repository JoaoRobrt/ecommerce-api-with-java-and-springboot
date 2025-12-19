package com.ecommerce.project.repositories;

import com.ecommerce.project.models.Category;
import com.ecommerce.project.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findProductByCategory(Category category, Pageable pageable);

    Optional<Product> findByProductNameIgnoreCase(String productName);
}
