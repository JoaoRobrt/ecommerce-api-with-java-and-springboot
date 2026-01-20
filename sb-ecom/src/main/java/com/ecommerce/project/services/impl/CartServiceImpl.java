package com.ecommerce.project.services.impl;

import com.ecommerce.project.dtos.responses.CartItemResponseDTO;
import com.ecommerce.project.dtos.responses.CartResponseDTO;
import com.ecommerce.project.mappers.CartItemMapper;
import com.ecommerce.project.mappers.ProductMapper;
import com.ecommerce.project.models.Cart;
import com.ecommerce.project.models.CartItem;
import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.services.CartItemService;
import com.ecommerce.project.services.CartService;
import com.ecommerce.project.services.ProductService;
import com.ecommerce.project.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final AuthUtil authUtil;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartResponseDTO addProductToCart(Long productId, Integer quantity) {
        Cart cart = getOrCreateUserCart();

        Product product = productService.findById(productId);

        CartItem cartItem = cartItemService.createCartItem(cart, product, quantity);

        cart.getCartItems().add(cartItem);

        cart.recalculateTotalPrice();

        cartRepository.save(cart);

        List<CartItem> cartItems = cart.getCartItems();

        List<CartItemResponseDTO> itemsDTOS = cartItems.stream().map(cartItemMapper :: toDTO).toList();

        return new CartResponseDTO(cart.getCartId(), cart.getTotalPrice(), itemsDTOS);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartResponseDTO> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(cart -> {

            List<CartItemResponseDTO> itemsDTOS = cart.getCartItems()
                    .stream()
                    .map(cartItemMapper :: toDTO)
                    .toList();
            return new CartResponseDTO(
                    cart.getCartId(),
                    cart.getTotalPrice(),
                    itemsDTOS
            );
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO findUserCart() {
        Cart userCart = getOrCreateUserCart();
        List<CartItemResponseDTO> itemsDTOs = userCart.getCartItems().stream().map(cartItemMapper :: toDTO).toList();
        return new CartResponseDTO(userCart.getCartId(), userCart.getTotalPrice(), itemsDTOs);
    }

    private Cart getOrCreateUserCart(){
        Cart userCart = cartRepository.findCartByUserId(authUtil.loggedInUserId());
        if(userCart != null)
            return userCart;
        Cart cart = new Cart();
        cart.setUser(authUtil.loggedInUser());
        return cartRepository.save(cart);
    }
}
