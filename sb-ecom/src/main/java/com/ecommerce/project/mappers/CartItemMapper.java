package com.ecommerce.project.mappers;

import com.ecommerce.project.dtos.responses.CartItemResponseDTO;
import com.ecommerce.project.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(source = "cartItemId", target = "cartItemId")
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.image", target = "image")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "product.specialPrice", target = "unitPrice")
    @Mapping(source = "product.discount", target = "discount")
    @Mapping(source = "subtotal", target = "subtotal")
    CartItemResponseDTO toDTO(CartItem cartItem);
}
