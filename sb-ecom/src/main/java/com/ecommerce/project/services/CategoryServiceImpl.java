package com.ecommerce.project.services;

import com.ecommerce.project.dtos.commoms.PageMetaDTO;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.CategoryRequestDTO;
import com.ecommerce.project.dtos.responses.CategoryResponseDTO;
import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.exceptions.ResourceNotFoudException;
import com.ecommerce.project.mappers.CategoryMapper;
import com.ecommerce.project.models.Category;
import com.ecommerce.project.repositories.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService{

    private  final CategoryMapper categoryMapper;

    private  final CategoryRepository categoryRepository;

    private static final Set<String> SORTABLE_FIELDS = Set.of("categoryName", "categoryId");


    @Override
    public PageResponseDTO<CategoryResponseDTO> findAll(Integer pageNumber, Integer pageSize,
                                                        String sortBy, String sortOrder) {
        String validatedSortBy = validateSortBy(sortBy);
        Sort.Direction direction = resolveSortOrder(sortOrder);

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, Sort.by(direction, validatedSortBy));

        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<CategoryResponseDTO> content = categoryPage.getContent()
                .stream()
                .map(categoryMapper :: toResponse)
                .toList();

        PageMetaDTO meta = new PageMetaDTO(
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.isFirst(),
                categoryPage.isLast()
                );

        return new PageResponseDTO<>(content, meta);

    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);

        String categoryName = dto.categoryName();
        checkCategoryNameUniqueness(categoryName);

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponseDTO delete(Long categoryId){
        Category foundedCategory = findById(categoryId);
        categoryRepository.delete(foundedCategory);
        return categoryMapper.toResponse(foundedCategory);
    }

    @Override
    public CategoryResponseDTO update(Long categoryId,CategoryRequestDTO dto) {
        Category category = findById(categoryId);

        checkCategoryNameUniqueness(categoryId, dto.categoryName());

        categoryMapper.updateEntityFromDto(dto, category);
        Category updated = categoryRepository.save(category);

        return categoryMapper.toResponse(updated);
    }

    //METODOS INTERNOS

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoudException("Category Not Found."));
    }

    private void checkCategoryNameUniqueness(String categoryName){
        String normalized = normalize(categoryName);
        categoryRepository.findByCategoryNameIgnoreCase(normalized).ifPresent( c -> {
            throw new ResourceAlreadyExistsException("Category with name '" + normalized + "' already exists.");
        });
    }

    private void checkCategoryNameUniqueness(Long categoryId, String categoryName) {
        String normalized = normalize(categoryName);
        categoryRepository.findByCategoryNameIgnoreCase(normalized)
                .ifPresent(existing -> {
                    if (!existing.getCategoryId().equals(categoryId)) {
                        throw new ResourceAlreadyExistsException(
                                "Category with name '" + normalized + "' already exists."
                        );
                    }
                });
    }

    private String normalize(String categoryName) {
        return categoryName.trim().toLowerCase();
    }
    private String validateSortBy(String sortBy){
        if (!SORTABLE_FIELDS.contains(sortBy)){
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }
        return sortBy;
    }

    private Sort.Direction resolveSortOrder(String sortOrder) {
        return "desc".equalsIgnoreCase(sortOrder)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
    }

}
