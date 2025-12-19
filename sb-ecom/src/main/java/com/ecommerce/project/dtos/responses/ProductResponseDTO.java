package com.ecommerce.project.dtos.responses;

public record ProductResponseDTO (Long id,
                                  String productName,
                                  String image,
                                  String description,
                                  Integer quantity,
                                  Double price,
                                  Double discount,
                                  Double specialPrice){
}
