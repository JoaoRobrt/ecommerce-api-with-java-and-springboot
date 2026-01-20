package com.ecommerce.project.dtos.responses;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO (Long cartId,
                               BigDecimal totalPrice,
                               List<CartItemResponseDTO> cartItems){
}
