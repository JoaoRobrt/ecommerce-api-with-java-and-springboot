package com.ecommerce.project.services;

import com.ecommerce.project.dtos.responses.CartResponseDTO;
import com.ecommerce.project.models.Cart;

import java.util.List;

public interface CartService {

    CartResponseDTO addProductToCart(Long productId, Integer quantity);
    List<CartResponseDTO> findAll();
    CartResponseDTO findUserCart();

    CartResponseDTO updateItemQuantity(Long cartItemId,Integer quantity);

    CartResponseDTO deleteItemFromCart(Long cartItemId);

}
