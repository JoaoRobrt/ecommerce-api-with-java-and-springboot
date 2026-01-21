package com.ecommerce.project.services;

import com.ecommerce.project.models.Cart;
import com.ecommerce.project.models.CartItem;
import com.ecommerce.project.models.Product;

public interface CartItemService {

    CartItem createCartItem(Cart cart, Product product, Integer quantity);

    Cart updateItemQuantity(Long cartItemId, Integer quantity);
}
