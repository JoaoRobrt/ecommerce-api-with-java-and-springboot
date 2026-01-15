package com.ecommerce.project.exceptions.domain.cart;

import com.ecommerce.project.exceptions.domain.DomainException;
import org.springframework.http.HttpStatus;

public class ProductAlreadyInCartException extends DomainException {
    public ProductAlreadyInCartException(String message) {
        super(HttpStatus.CONFLICT, "Product is already in the cart");
    }
}
