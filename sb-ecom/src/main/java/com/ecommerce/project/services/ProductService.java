package com.ecommerce.project.services;

import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO addProduct(Long categoryId, ProductRequestDTO dto);
}
