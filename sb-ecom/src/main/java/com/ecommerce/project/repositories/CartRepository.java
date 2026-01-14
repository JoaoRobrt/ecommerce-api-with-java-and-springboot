package com.ecommerce.project.repositories;

import com.ecommerce.project.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
