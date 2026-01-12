package com.ecommerce.project.services.impl;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.ProductRequestDTO;
import com.ecommerce.project.dtos.responses.ProductResponseDTO;
import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.mappers.ProductMapper;
import com.ecommerce.project.models.Category;
import com.ecommerce.project.models.Product;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.services.CategoryService;
import com.ecommerce.project.services.FileService;
import com.ecommerce.project.services.ProductService;
import com.ecommerce.project.utils.PageableUtils;
import com.ecommerce.project.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Set<String> SORTABLE_FIELDS = Set.of("productName", "productId", "price", "discount");

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryService categoryService;

    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductResponseDTO addProduct(Long categoryId, ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);

        if (productRepository.existsByProductName(product.getProductName())){
            throw new ResourceAlreadyExistsException("Product name is already taken.");
        }

        Category category = categoryService.findById(categoryId);

        if (dto.image() != null) {
            product.setImage(dto.image());
        } else {
            product.setImage("default.png");
        }

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

    @Override
    public PageResponseDTO<ProductResponseDTO> searchByKeyword(String keyword, Integer pageNumber,
                                                               Integer pageSize, String sortBy, String sortOrder) {

        Pageable pageable = PageableUtils.createPageable(pageNumber, pageSize, sortBy, sortOrder,
                SORTABLE_FIELDS, AppConstants.SORT_PRODUCTS_BY);

        String keywordPattern = "%" + keyword.toLowerCase() + "%";

        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase(keywordPattern, pageable);

        return PaginationUtils.toPageResponseDTO(productPage, productMapper :: toResponse);
    }

    @Override
    public ProductResponseDTO update(Long productId, ProductRequestDTO dto) {
        Product product = findById(productId);

        if (productRepository.existsByProductNameAndProductIdNot(product.getProductName(), productId)){
            throw new ResourceAlreadyExistsException("Product name is already taken.");
        }

        product.setProductName(dto.productName());
        product.setImage(dto.image());
        product.setDescription(dto.description());
        product.setQuantity(dto.quantity());
        product.setPrice(dto.price());
        product.setDiscount(dto.discount());

        Product saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }

    @Override
    public ProductResponseDTO delete(Long productId) {
        Product foundedProduct = findById(productId);
        productRepository.delete(foundedProduct);
        return productMapper.toResponse(foundedProduct);
    }

    @Override
    public ProductResponseDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product foundedProduct = findById(productId);

        String fileName = fileService.uploadImage(path, image);

        foundedProduct.setImage(fileName);

        Product updatedProduct = productRepository.save(foundedProduct);

        return productMapper.toResponse(updatedProduct);
    }


    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found."));
    }

}
