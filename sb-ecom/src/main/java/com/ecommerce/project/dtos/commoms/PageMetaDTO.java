package com.ecommerce.project.dtos.commoms;

public record PageMetaDTO(
        int number,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last) {
}

