package com.ecommerce.project.mappers;

import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;
import com.ecommerce.project.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<Product, ProductRequestDTO, ProductResponseDTO>{

    @Override
    @Mapping(source = "productId", target = "productId")
    ProductResponseDTO toResponse(Product entity);

}
