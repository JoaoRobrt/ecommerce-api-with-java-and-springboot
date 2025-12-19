package com.ecommerce.project.controllers;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.CategoryRequestDTO;
import com.ecommerce.project.dtos.commoms.ApiResponse;
import com.ecommerce.project.dtos.responses.CategoryResponseDTO;
import com.ecommerce.project.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/public/category")
    public ResponseEntity<ApiResponse<PageResponseDTO<CategoryResponseDTO>>> getAllCategories(
    @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) @Min(0)Integer pageNumber,
    @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE )@Min(1) Integer pageSize,
    @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
    @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder) {
        PageResponseDTO<CategoryResponseDTO> categories = categoryService.findAll(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(ApiResponse.success( "Categories retrieved successfully", categories));
    }

    @PostMapping("/admin/category")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO createdCategory = categoryService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Category created successfully", createdCategory));
    }

    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(@PathVariable Long categoryId,
                                                              @Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO updatedCategory = categoryService.update(categoryId, dto);
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", updatedCategory));
    }

    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> deleteCategory(@PathVariable Long categoryId) {
        CategoryResponseDTO categoryResponseDTO = categoryService.delete(categoryId);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", categoryResponseDTO));
    }
}
