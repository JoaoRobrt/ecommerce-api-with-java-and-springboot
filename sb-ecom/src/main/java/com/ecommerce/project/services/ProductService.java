package com.ecommerce.project.services;

import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductResponseDTO addProduct(Long categoryId, ProductRequestDTO dto);

    PageResponseDTO<ProductResponseDTO> findAll(Integer pageNumber,Integer pageSize,
                                                String sortBy, String sortOrder);

    PageResponseDTO<ProductResponseDTO> searchByCategory(Long categoryId, Integer pageNumber,
                                                         Integer pageSize, String sortBy,
                                                         String sortOrder);

    PageResponseDTO<ProductResponseDTO> searchByKeyword(String keyword, Integer pageNumber,
                                                        Integer pageSize, String sortBy,
                                                        String sortOrder);

    ProductResponseDTO update(Long productId, ProductRequestDTO dto);

    ProductResponseDTO delete(@NotNull Long productId);

    ProductResponseDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
