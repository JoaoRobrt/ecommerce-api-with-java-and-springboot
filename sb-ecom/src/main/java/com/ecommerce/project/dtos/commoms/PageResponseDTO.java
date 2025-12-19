package com.ecommerce.project.dtos.commoms;

import java.util.List;

public record PageResponseDTO<T>(
        List<T> content,
        PageMetaDTO page) {
}
