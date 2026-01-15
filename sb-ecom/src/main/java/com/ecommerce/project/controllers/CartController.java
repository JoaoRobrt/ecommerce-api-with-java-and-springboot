package com.ecommerce.project.controllers;

import com.ecommerce.project.dtos.commoms.ApiResponse;
import com.ecommerce.project.dtos.responses.CartResponseDTO;
import com.ecommerce.project.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("cart/product/{productId}/quantity/{quantity}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> addProductToCart(@PathVariable Long productId,
                                                                         @PathVariable Integer quantity){
        CartResponseDTO cartDTO = cartService.addProductToCart(productId, quantity);

        return ResponseEntity.ok(ApiResponse.success("Product added to the cart", cartDTO));
    }
}
