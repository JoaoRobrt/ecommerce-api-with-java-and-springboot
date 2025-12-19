package com.ecommerce.project.controllers;

import com.ecommerce.project.dtos.commoms.ApiResponse;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;
import com.ecommerce.project.services.ProductService;
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
    public ResponseEntity<ApiResponse<ProductResponseDTO>> addProduct(@RequestBody ProductRequestDTO dto,
                                                                     @PathVariable Long categoryId){
        ProductResponseDTO body = productService.addProduct(categoryId, dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully",body));
    }
}
