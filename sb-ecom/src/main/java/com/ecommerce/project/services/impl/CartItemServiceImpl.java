package com.ecommerce.project.services.impl;

import com.ecommerce.project.exceptions.domain.cart.ProductAlreadyInCartException;
import com.ecommerce.project.exceptions.domain.stock.InsufficientStockException;
import com.ecommerce.project.exceptions.domain.stock.OutOfStockException;
import com.ecommerce.project.models.Cart;
import com.ecommerce.project.models.CartItem;
import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem createCartItem(Cart cart, Product product, Integer quantity) {

        CartItem cartItem = cartItemRepository
                .findCartItemByProductIdAndCartId(cart.getCartId(), product.getProductId());

        int stock = product.getQuantity();

        if (stock == 0) {
            throw new OutOfStockException(product.getProductName());
        }

        int finalQuantity = quantity;
        if (cartItem != null) {
            finalQuantity += cartItem.getQuantity();
        }

        if (finalQuantity > stock) {
            throw new InsufficientStockException(finalQuantity, stock);
        }

        if (cartItem != null) {
            cartItem.setQuantity(finalQuantity);
            return cartItemRepository.save(cartItem);
        }

        CartItem newCartItem = new CartItem(
                product,
                cart,
                quantity,
                product.getDiscount(),
                product.getSpecialPrice()
        );

        return cartItemRepository.save(newCartItem);
    }
}
