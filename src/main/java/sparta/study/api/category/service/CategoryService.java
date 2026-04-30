package sparta.study.api.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.study.api.category.dto.CategoryCreate;
import sparta.study.api.category.dto.CategoryResponse;
import sparta.study.api.category.entity.Category;
import sparta.study.api.category.repository.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryCreate categoryCreate) {
        Category category = Category.builder()
                .name(categoryCreate.name()).build();

        categoryRepository.save(category);

        return new CategoryResponse(category.getId(), category.getName());
    }
}
