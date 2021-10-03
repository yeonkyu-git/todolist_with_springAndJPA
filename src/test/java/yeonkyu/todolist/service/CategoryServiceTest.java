package yeonkyu.todolist.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    Member member1 = new Member("joowon", "skyblue9221@gmail.com", LocalDateTime.now());


    @Test
    public void 카테고리_등록() throws Exception {
        //given
        Category category = new Category(member1, "work", LocalDateTime.now());

        //when
        Long categoryId = categoryService.enrollCategory(category);
        Category findCategory = categoryRepository.findOne(categoryId);

        //then
        Assertions.assertThat(category).isEqualTo(findCategory);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복등록금지() throws Exception {
        //given
        Category category1 = new Category(member1, "work", LocalDateTime.now());
        Category category2 = new Category(member1, "work", LocalDateTime.now());

        //when
        categoryService.enrollCategory(category1);
        categoryService.enrollCategory(category2);

        //then
        fail("잘못되었습니다.");
    }

    @Test
    public void 카테고리삭제() throws Exception {
        //given
        Category category1 = new Category(member1, "work", LocalDateTime.now());
        Category category2 = new Category(member1, "study", LocalDateTime.now());
        categoryService.enrollCategory(category1);
        categoryService.enrollCategory(category2);

        //when
        categoryService.deleteCategory(category1.getId());
        List<Category> categories = categoryService.findCategories(member1.getId());

        //then
        Assertions.assertThat(categories.size()).isEqualTo(1);
    }

    @Test
    public void 카테고리수정() throws Exception {
        //given
        Category category1 = new Category(member1, "work", LocalDateTime.now());
        Long categoryId = categoryService.enrollCategory(category1);

        //when
        String updateName = "study";
        categoryService.updateCategory(categoryId, updateName);

        //then
        Assertions.assertThat(category1.getName()).isEqualTo("study");
    }

}