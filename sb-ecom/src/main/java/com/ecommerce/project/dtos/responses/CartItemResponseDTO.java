package com.ecommerce.project.dtos.responses;

public record CartItemResponseDTO (Long cartItemId,
                                   CartResponseDTO cart,
                                   ProductResponseDTO product,
                                   Integer quantity,
                                   Double discount,
                                   Double productPrice){
}
