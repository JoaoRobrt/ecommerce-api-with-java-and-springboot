package com.ecommerce.project.services;

import com.ecommerce.project.dtos.responses.CartResponseDTO;

public interface CartService {

    CartResponseDTO addProductToCart(Long productId, Integer quantity);
}
