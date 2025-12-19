package com.ecommerce.project.controllers;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.dtos.commoms.ApiResponse;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;
import com.ecommerce.project.services.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin/category/{categoryId}/product")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> addProduct(@Valid @RequestBody ProductRequestDTO dto,
                                                                     @PathVariable Long categoryId){

        ProductResponseDTO body = productService.addProduct(categoryId, dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully",body));
    }

    @GetMapping("/public/product")
    public ResponseEntity<ApiResponse<PageResponseDTO<ProductResponseDTO>>> getAllProducts(
            @Valid
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) @Min(0) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE)@Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue =  AppConstants.SORT_DIR) String sortOrder
    ){
        PageResponseDTO<ProductResponseDTO> page = productService.findAll(pageNumber, pageSize,sortBy,sortOrder);

        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully" ,page));
    }

    @GetMapping("/public/category/{categoryId}/product")
    public ResponseEntity<ApiResponse<PageResponseDTO<ProductResponseDTO>>> getProductsByCategory(
            @Valid
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) @Min(0) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE)@Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue =  AppConstants.SORT_DIR) String sortOrder){

        PageResponseDTO<ProductResponseDTO> page =  productService.searchByCategory(categoryId, pageNumber,
                pageSize, sortBy, sortOrder);

        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully" ,page));
    }

    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ApiResponse<PageResponseDTO<ProductResponseDTO>>> getProductsByKeyword(
            @Valid
            @NonNull @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) @Min(0) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE)@Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue =  AppConstants.SORT_DIR) String sortOrder){

        PageResponseDTO<ProductResponseDTO> page = productService.searchByKeyword(keyword, pageNumber,
                pageSize, sortBy, sortOrder );

        return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", page));

    }

    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDTO dto){

        ProductResponseDTO responseDTO = productService.update(productId, dto);

        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", responseDTO));
    }

    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> delete(@NotNull @PathVariable Long productId){
        ProductResponseDTO responseDTO = productService.delete(productId);
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", responseDTO));
    }
}
