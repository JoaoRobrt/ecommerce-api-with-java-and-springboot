package com.ecommerce.project.dtos.commoms;

import org.springframework.data.domain.Sort;

public record SortMetaDTO(String property, String direction) {

    public static SortMetaDTO fromSort(Sort.Order order){
        return new SortMetaDTO(order.getProperty(), order.getDirection().name());
    }
}
