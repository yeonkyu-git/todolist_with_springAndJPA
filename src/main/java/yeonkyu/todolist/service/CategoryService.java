package yeonkyu.todolist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.repository.CategoryRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 등록
     * @param category
     * @return
     */
    @Transactional
    public Long enrollCategory(Category category) {
        validateDuplicateCategory(category);
        categoryRepository.save(category);
        return category.getId();
    }

    private void validateDuplicateCategory(Category category) {
        List<Category> categories = categoryRepository.findByName(category.getName());
        if (!categories.isEmpty()) {
            throw new IllegalStateException("동일한 카테고리 이름이 존재합니다.");
        }
    }

    /**
     * 카테고리 찾기 (by Id)
     * @param categoryId
     * @return
     */
    public Category findOne(Long categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    /**
     * 카테고리 찾기 (by Name)
     * @param categoryName
     * @return
     */
    public List<Category> findByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    /**
     * 모든 카테고리 조회
     * @return
     */
    public List<Category> findCategories(Long memberId) {
        return categoryRepository.findByMember(memberId);
    }

    /**
     * 카테고리 수정
     * @param categoryId
     * @param updateName
     */
    @Transactional
    public void updateCategory(Long categoryId, String updateName) {
        Category category = categoryRepository.findOne(categoryId);
        category.changeCategoryName(updateName);
    }

    /**
     * 카테고리 삭제
     * @param categoryId
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findOne(categoryId);
        categoryRepository.delete(category);
    }


}
