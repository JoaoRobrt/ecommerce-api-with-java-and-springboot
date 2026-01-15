package com.ecommerce.project.exceptions.domain.stock;

import com.ecommerce.project.exceptions.domain.DomainException;
import org.springframework.http.HttpStatus;

public class OutOfStockException extends DomainException {
    public OutOfStockException(String resource) {
        super(HttpStatus.CONFLICT, resource + ": is ou of stock");
    }
}
