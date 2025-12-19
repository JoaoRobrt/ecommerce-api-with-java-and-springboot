package com.ecommerce.project.services;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import com.ecommerce.project.dtos.requests.CategoryRequestDTO;
import com.ecommerce.project.dtos.responses.CategoryResponseDTO;
import com.ecommerce.project.exceptions.ResourceNotFoudException;
import com.ecommerce.project.mappers.CategoryMapper;
import com.ecommerce.project.models.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.utils.PaginationUtils;
import com.ecommerce.project.utils.SortUtils;
import com.ecommerce.project.utils.UniquenessChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private  final CategoryMapper categoryMapper;

    private  final CategoryRepository categoryRepository;

    private static final Set<String> SORTABLE_FIELDS = Set.of("categoryName", "categoryId");

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);

        String categoryName = dto.categoryName();

        UniquenessChecker.checkNameUniqueness(
                categoryName,
                categoryRepository::findByCategoryNameIgnoreCase,
                Category::getCategoryId,
                null,
                "Category"
        );

        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public PageResponseDTO<CategoryResponseDTO> findAll(Integer pageNumber, Integer pageSize,
                                                        String sortBy, String sortOrder) {

        Sort sort = SortUtils.buildSort(sortBy,sortOrder, SORTABLE_FIELDS, AppConstants.SORT_PRODUCTS_BY);

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        return PaginationUtils.toPageResponseDTO(categoryPage, categoryMapper :: toResponse);

    }

    @Override
    public CategoryResponseDTO update(Long categoryId,CategoryRequestDTO dto) {
        Category category = findById(categoryId);

        UniquenessChecker.checkNameUniqueness(
                category.getCategoryName(),
                categoryRepository::findByCategoryNameIgnoreCase,
                Category::getCategoryId,
                categoryId,
                "Category"
        );

        categoryMapper.updateEntityFromDto(dto, category);
        Category updated = categoryRepository.save(category);

        return categoryMapper.toResponse(updated);
    }

    @Override
    public CategoryResponseDTO delete(Long categoryId){
        Category foundedCategory = findById(categoryId);
        categoryRepository.delete(foundedCategory);
        return categoryMapper.toResponse(foundedCategory);
    }

    //METODOS INTERNOS

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoudException("Category Not Found."));
    }


}
