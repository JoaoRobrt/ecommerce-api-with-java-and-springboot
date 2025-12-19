package com.ecommerce.project.mappers;

import com.ecommerce.project.dtos.requests.CategoryRequestDTO;
import com.ecommerce.project.dtos.responses.CategoryResponseDTO;
import com.ecommerce.project.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toResponse(Category category);

    Category toEntity (CategoryRequestDTO dto);

    void updateEntityFromDto(CategoryRequestDTO dto, @MappingTarget Category entity);

}
