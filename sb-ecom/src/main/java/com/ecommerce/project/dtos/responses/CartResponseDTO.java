package com.ecommerce.project.dtos.responses;

import java.util.List;

public record CartResponseDTO (Long cartId,
                               Double totalPrice,
                               List<ProductResponseDTO> products){
}
