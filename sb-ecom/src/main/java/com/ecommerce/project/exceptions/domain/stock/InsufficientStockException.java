package com.ecommerce.project.exceptions.domain.stock;

import com.ecommerce.project.exceptions.domain.DomainException;
import org.springframework.http.HttpStatus;

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(int requested, int available) {
        super( HttpStatus.CONFLICT,
                "Requested quantity (" + requested + ") exceeds available stock (" + available + ")");
    }
}
