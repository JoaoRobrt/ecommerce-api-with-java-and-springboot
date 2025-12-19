package com.ecommerce.project.dtos.requests;

public record ProductRequestDTO(String productName,
                                String image,
                                String description,
                                Integer quantity,
                                Double price,
                                Double discount)  {
}
