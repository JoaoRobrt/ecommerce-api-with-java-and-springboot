package com.ecommerce.project.mappers;

import com.ecommerce.project.dtos.responses.CartItemResponseDTO;
import com.ecommerce.project.models.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemResponseDTO toDTO(CartItem cartItem);
}
