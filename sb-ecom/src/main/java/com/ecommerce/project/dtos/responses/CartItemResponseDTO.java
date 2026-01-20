package com.ecommerce.project.dtos.responses;

public record CartItemResponseDTO (    Long cartItemId,

                                       Long productId,
                                       String productName,
                                       String image,

                                       Integer quantity,

                                       Double unitPrice,
                                       Double discount,
                                       Double subtotal)
{
}
