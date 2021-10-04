package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.CategoryRepository;
import yeonkyu.todolist.service.CategoryService;
import yeonkyu.todolist.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final MemberService memberService;
    private final HttpServletRequest request;

    /**
     * 카테고리 등록
     * @param categoryName
     * @return
     */
    @PostMapping("/category/create")
    public String createCategory(@RequestParam String categoryName, Model model) {
        // Session에 저장된 값 불러오기
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("memberId");
        Member findMember = memberService.findOne(memberId);
        Category category = new Category(findMember, categoryName, LocalDateTime.now());
        categoryService.enrollCategory(category);
        return "redirect:/todolist"; // 템플릿뷰 만들어야 함
    }

    /**
     * 카테고리 수정
     * @param categoryId
     * @param categoryName
     * @return
     */
    @PostMapping("/category/update")
    public String updateCategory(@RequestParam Long categoryId, @RequestParam String categoryName) {
        categoryService.updateCategory(categoryId, categoryName);
        return "todolist"; // 템플릿뷰 만들어야 함
    }

    /**
     * 카테고리 삭제
     * @param categoryId
     * @return
     */
    @PostMapping("/category/delete")
    public String deleteCategory(@RequestParam Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return "todolist"; // 템플릿뷰 만들어야 함
    }
}
