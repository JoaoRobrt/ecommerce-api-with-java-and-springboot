package com.ecommerce.project.dtos.requests;

import jakarta.validation.constraints.*;

public record ProductRequestDTO(
                                @NotBlank(message = "Product name is required.")
                                @Size(min = 4, message = "Product name must have at least 4 characters.")
                                String productName,
                                String image,
                                String description,

                                @NotNull(message = "Product quantity value is required.")
                                @Positive(message = "quantity must be greater than zero")
                                Integer quantity,

                                @NotNull(message = "Product price value is required.")
                                @Positive(message = "Price must be greater than zero")
                                Double price,
                                @NotNull(message = "Product discount value is required.")
                                @PositiveOrZero(message = "Discount can't be a negative number.")
                                Double discount)  {
}
