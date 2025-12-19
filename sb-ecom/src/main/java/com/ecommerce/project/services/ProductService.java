package com.ecommerce.project.services;

import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO addProduct(Long categoryId, ProductRequestDTO dto);

    PageResponseDTO<ProductResponseDTO> findAll(Integer pageNumber,Integer pageSize,
                                                String sortBy, String sortOrder);

    PageResponseDTO<ProductResponseDTO> searchByCategory(Long categoryId, Integer pageNumber,
                                                         Integer pageSize, String sortBy,
                                                         String sortOrder);
}
