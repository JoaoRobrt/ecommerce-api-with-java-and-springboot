package com.ecommerce.project.dtos.requests;

import jakarta.validation.constraints.Min;

public record UpdateCartItemQuantityRequest(@Min(0) Integer quantity) {
}
