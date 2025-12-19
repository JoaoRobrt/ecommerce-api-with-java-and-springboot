package com.ecommerce.project.utils;

import org.springframework.data.domain.Sort;

import java.util.Set;

public final class SortUtils {

    private SortUtils(){}

    public static Sort buildSort(
            String sortBy,
            String sortOrder,
            Set<String> allowedFields,
            String defaultField
    ) {
        String field = allowedFields.contains(sortBy) ? sortBy : defaultField;

        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortOrder)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        return Sort.by(direction, field);
    }

}
