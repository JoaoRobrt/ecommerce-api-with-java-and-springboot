package com.ecommerce.project.mappers;

import com.ecommerce.project.dtos.responses.CartItemResponseDTO;
import com.ecommerce.project.models.CartItem;

public interface CartItemMapper {

    CartItemResponseDTO toDTO(CartItem cartItem);
}
