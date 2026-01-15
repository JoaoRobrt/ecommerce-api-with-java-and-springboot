package com.ecommerce.project.services.impl;

import com.ecommerce.project.dtos.responses.CartResponseDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;
import com.ecommerce.project.mappers.CartMapper;
import com.ecommerce.project.mappers.ProductMapper;
import com.ecommerce.project.models.Cart;
import com.ecommerce.project.models.CartItem;
import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.services.CartItemService;
import com.ecommerce.project.services.CartService;
import com.ecommerce.project.services.ProductService;
import com.ecommerce.project.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final AuthUtil authUtil;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final ProductMapper productMapper;
    private final CartItemService cartItemService;

    @Override
    public CartResponseDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = createCart();

        Product product = productService.findById(productId);

        CartItem cartItem = cartItemService.createCartItem(cart, product, quantity);

        product.setQuantity(product.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));

        cartRepository.save(cart);

        List<CartItem> cartItems = cart.getCartItems();

        List<ProductResponseDTO> productResponseDTOS = cartItems.stream().map(item ->
                new ProductResponseDTO(
                        item.getProduct().getProductId(),
                        item.getProduct().getProductName(),
                        item.getProduct().getImage(),
                        item.getProduct().getDescription(),
                        item.getProduct().getQuantity(),
                        item.getProduct().getPrice(),
                        item.getProduct().getDiscount(),
                        item.getProduct().getSpecialPrice())
        ).toList();

        return new CartResponseDTO(cart.getCartId(), cart.getTotalPrice(), productResponseDTOS);
    }

    private Cart createCart(){
        Cart userCart = cartRepository.findCartByUserId(authUtil.loggedInUserId());
        if(userCart != null)
            return userCart;
        Cart cart = new Cart();
        cart.setUser(authUtil.loggedInUser());
        return cartRepository.save(cart);
    }
}
