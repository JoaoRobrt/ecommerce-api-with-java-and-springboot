package com.ecommerce.project.controllers;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.CategoryRequestDTO;
import com.ecommerce.project.dtos.responses.CategoryResponseDTO;
import com.ecommerce.project.models.Category;
import com.ecommerce.project.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @GetMapping("/public/category")
    public ResponseEntity<PageResponseDTO<CategoryResponseDTO>> getAllCategories(
    @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) @Min(0)Integer pageNumber,
    @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE )@Min(1) Integer pageSize,
    @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
    @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR) String sortOrder) {
        PageResponseDTO<CategoryResponseDTO> categories = categoryService.findAll(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO createdCategory = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long categoryId,
                                                              @Valid @RequestBody CategoryRequestDTO dto) {
        CategoryResponseDTO updatedCategory = categoryService.update(categoryId, dto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryResponseDTO categoryResponseDTO = categoryService.delete(categoryId);
        return ResponseEntity.ok(categoryResponseDTO);
    }
}
