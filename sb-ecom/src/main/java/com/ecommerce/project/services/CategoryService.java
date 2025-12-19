package com.ecommerce.project.services;

import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.CategoryRequestDTO;
import com.ecommerce.project.dtos.responses.CategoryResponseDTO;
import com.ecommerce.project.models.Category;

import java.util.List;


public interface CategoryService {

    PageResponseDTO<CategoryResponseDTO> findAll(Integer pageNumber,
                                                 Integer pageSize,
                                                 String sortBy,
                                                 String sortOrder);
    CategoryResponseDTO create(CategoryRequestDTO dto);
    CategoryResponseDTO delete(Long categoryId);
    CategoryResponseDTO update(Long categoryId,CategoryRequestDTO dto);
}
