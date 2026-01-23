package com.ecommerce.project.services.impl;

import com.ecommerce.project.exceptions.api.ResourceNotFoundException;
import com.ecommerce.project.exceptions.domain.stock.InsufficientStockException;
import com.ecommerce.project.exceptions.domain.stock.OutOfStockException;
import com.ecommerce.project.models.Cart;
import com.ecommerce.project.models.CartItem;
import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.services.CartItemService;
import com.ecommerce.project.utils.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final AuthUtil authUtil;

    @Override
    public CartItem createCartItem(Cart cart, Product product, Integer quantity) {
        CartItem cartItem = cartItemRepository
                .findCartItemByProductIdAndCartId(cart.getCartId(), product.getProductId());

        int stock = product.getQuantity();
        if (stock == 0) throw new OutOfStockException(product.getProductName());

        int finalQuantity = quantity;
        if (cartItem != null) {
            finalQuantity += cartItem.getQuantity();
        }

        if (finalQuantity > stock) throw new InsufficientStockException(finalQuantity, stock);

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

        CartItem savedItem = cartItemRepository.save(newCartItem);
        cart.getCartItems().add(savedItem);

        return savedItem;
    }

    @Override
    @Transactional
    public Cart updateItemQuantity(Long cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        Long authUserId = authUtil.loggedInUserId();
        if (!item.getCart().getUser().getUserId().equals(authUserId)) {
            throw new AccessDeniedException("Cart item does not belong to authenticated user");
        }

        int stock = item.getProduct().getQuantity();

        if (quantity <= 0) {
            item.getCart().removeItem(item);
            cartItemRepository.delete(item);
        } else {

            if (quantity > stock) {
                throw new InsufficientStockException(quantity, stock);
            }
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }

        item.getCart().recalculateTotalPrice();

        return item.getCart();
    }

    @Override
    @Transactional
    public Cart deleteCartItem(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        Long authUserId = authUtil.loggedInUserId();
        Cart cart = item.getCart();

        if (!cart.getUser().getUserId().equals(authUserId)) {
            throw new AccessDeniedException("Cart item does not belong to authenticated user");
        }

        cart.removeItem(item);
        cartItemRepository.delete(item);
        cartItemRepository.flush();

        cart.recalculateTotalPrice();

        return cart;
    }


}
