package com.ecommerce.project.services;

import com.ecommerce.project.dtos.responses.CartResponseDTO;
import jakarta.validation.constraints.Min;

import java.util.List;

public interface CartService {

    CartResponseDTO addProductToCart(Long productId, Integer quantity);
    List<CartResponseDTO> findAll();
    CartResponseDTO findUserCart();

    CartResponseDTO updateItemQuantity(Long cartItemId,Integer quantity);
}
