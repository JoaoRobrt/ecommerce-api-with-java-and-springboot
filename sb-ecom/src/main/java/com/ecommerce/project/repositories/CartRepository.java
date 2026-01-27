package com.ecommerce.project.repositories;

import com.ecommerce.project.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.userId = ?1")
    Cart findCartByUserId(Long userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci WHERE c.user.userId = :userId")
    Cart findByUserIdWithItems(@Param("userId") Long userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems")
    List<Cart> findAllWithItems();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cart c WHERE c.user.userId = :userId")
    boolean existsByUserId(@Param("userId") Long userId);

}
