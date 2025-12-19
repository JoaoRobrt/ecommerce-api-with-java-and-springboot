package com.ecommerce.project.mappers;

import com.ecommerce.project.dtos.requests.CategoryRequestDTO;
import com.ecommerce.project.dtos.responses.CategoryResponseDTO;
import com.ecommerce.project.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<Category, CategoryRequestDTO, CategoryResponseDTO>{

}
