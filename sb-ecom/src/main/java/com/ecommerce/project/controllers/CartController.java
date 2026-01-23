package com.ecommerce.project.controllers;

import com.ecommerce.project.dtos.commoms.ApiResponse;
import com.ecommerce.project.dtos.requests.UpdateCartItemQuantityRequest;
import com.ecommerce.project.dtos.responses.CartResponseDTO;
import com.ecommerce.project.services.CartService;
import jakarta.validation.Valid;
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
    // Get authenticated user cart
    @GetMapping("/cart/user")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getAuthUserCart(){
        CartResponseDTO body = cartService.findUserCart();

        return ResponseEntity.ok(ApiResponse.success("Authenticated user cart retrieved successfully", body));
    }

    @PatchMapping("/cart/item/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> updateItemQuantity(@PathVariable Long cartItemId,
                                                                           @RequestBody @Valid UpdateCartItemQuantityRequest request
    ){
      CartResponseDTO body = cartService.updateItemQuantity(cartItemId, request.quantity());
      return ResponseEntity.ok(ApiResponse.success("Cart Item updated", body));
    }

    @DeleteMapping("/cart/item/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> deleteItemFromCart(@PathVariable Long cartItemId){
        CartResponseDTO body = cartService.deleteItemFromCart(cartItemId);
        return ResponseEntity.ok(ApiResponse.success("Item deleted successfully", body));
    }

}
