package com.ecommerce.project.exceptions.api;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String resource){
        super(HttpStatus.NOT_FOUND, resource + " not found");
    }
}
