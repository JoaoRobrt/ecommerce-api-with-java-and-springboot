package com.ecommerce.project.exceptions.api;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends ApiException {
    public ResourceAlreadyExistsException(String resource) {
        super(HttpStatus.CONFLICT, resource + " already exists");
    }
}
