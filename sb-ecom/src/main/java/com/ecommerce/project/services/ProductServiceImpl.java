package com.ecommerce.project.services;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.dtos.commoms.PageMetaDTO;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;
import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.exceptions.ResourceNotFoudException;
import com.ecommerce.project.mappers.ProductMapper;
import com.ecommerce.project.models.Category;
import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.utils.PageableUtils;
import com.ecommerce.project.utils.PaginationUtils;
import com.ecommerce.project.utils.SortUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private static final Set<String> SORTABLE_FIELDS = Set.of("productName", "productId", "price", "discount");

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryService categoryService;

    @Override
    public ProductResponseDTO addProduct(Long categoryId, ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        checkNameUniqueness(product.getProductName());
        Category category = categoryService.findById(categoryId);

        product.setImage("defaul.png");
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public PageResponseDTO<ProductResponseDTO> findAll(Integer pageNumber, Integer pageSize,
                                                       String sortBy, String sortOrder) {

        Pageable pageable = PageableUtils.createPageable(pageNumber, pageSize, sortBy, sortOrder,
                SORTABLE_FIELDS, AppConstants.SORT_PRODUCTS_BY);

        Page<Product> productPage = productRepository.findAll(pageable);

        return PaginationUtils.toPageResponseDTO(productPage, productMapper::toResponse);
    }

    @Override
    public PageResponseDTO<ProductResponseDTO> searchByCategory(Long categoryId, Integer pageNumber,
                                                                Integer pageSize, String sortBy, String sortOrder) {

        Category category = categoryService.findById(categoryId);

        Pageable pageable = PageableUtils.createPageable(pageNumber, pageSize, sortBy, sortOrder,
                SORTABLE_FIELDS, AppConstants.SORT_PRODUCTS_BY);

        Page<Product> productPage = productRepository.findProductByCategory(category, pageable);

        return PaginationUtils.toPageResponseDTO(productPage, productMapper :: toResponse);
    }


    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoudException("Product Not Found."));
    }

    private void checkNameUniqueness(String productName){
        String normalized = SortUtils.normalize(productName);
        productRepository.findByProductNameIgnoreCase(normalized).ifPresent( c -> {
            throw new ResourceAlreadyExistsException("Product with name '" + normalized + "' already exists.");
        });
    }

    private void checkNameUniqueness(Long productId, String productName) {
        String normalized = SortUtils.normalize(productName);
        productRepository.findByProductNameIgnoreCase(normalized)
                .ifPresent(existing -> {
                    if (!existing.getProductId().equals(productId)) {
                        throw new ResourceAlreadyExistsException(
                                "Product with name '" + normalized + "' already exists."
                        );
                    }
                });
    }



}
