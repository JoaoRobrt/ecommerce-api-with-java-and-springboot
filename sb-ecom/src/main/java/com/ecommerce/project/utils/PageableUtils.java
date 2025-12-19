package com.ecommerce.project.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class PageableUtils {

    public static Pageable createPageable(Integer pageNumber, Integer pageSize,
                                          String sortBy, String sortOrder,
                                          Set<String> sortableFields, String defaultSortBy){

        Sort sort = SortUtils.buildSort(sortBy, sortOrder, sortableFields, defaultSortBy);
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
