package com.ecommerce.project.controllers;

import com.ecommerce.project.dtos.commoms.ApiResponse;
import com.ecommerce.project.dtos.responses.CartResponseDTO;
import com.ecommerce.project.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("cart/product/{productId}/quantity/{quantity}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> addProductToCart(@PathVariable Long productId,
                                                                         @PathVariable Integer quantity){
        CartResponseDTO body = cartService.addProductToCart(productId, quantity);

        return ResponseEntity.ok(ApiResponse.success("Product added to the cart", body));
    }

    @GetMapping("/cart")
    public ResponseEntity<ApiResponse<List<CartResponseDTO>>> getAllCarts(){
        List<CartResponseDTO> body = cartService.findAll();
        return ResponseEntity.ok(ApiResponse.success("All carts retrieved successfully", body));
    }

    @GetMapping("/cart/user")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getAuthUserCart(){
        CartResponseDTO body = cartService.findUserCart();

        return ResponseEntity.ok(ApiResponse.success("Authenticated user cart retrieved successfully", body));
    }

}
