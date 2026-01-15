package com.ecommerce.project.mappers;

import com.ecommerce.project.dtos.responses.CartResponseDTO;
import com.ecommerce.project.models.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "cartId", target = "cartId")
    CartResponseDTO toDTO(Cart cart);
}
