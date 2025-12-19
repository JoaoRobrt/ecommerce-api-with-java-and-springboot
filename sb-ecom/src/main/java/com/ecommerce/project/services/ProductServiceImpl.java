package com.ecommerce.project.services;

import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;
import com.ecommerce.project.mappers.ProductMapper;
import com.ecommerce.project.models.Category;
import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{


    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryService categoryService;


    @Override
    public ProductResponseDTO addProduct(Long categoryId, ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        Category category = categoryService.findById(categoryId);

        product.setImage("defaul.png");
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

}
