package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.service.CategoryService;
import yeonkyu.todolist.service.MemberService;
import yeonkyu.todolist.service.TodoService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class Homecontroller {

    private final HttpServletRequest request;

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final TodoService todoService;

    /**
     * Home & login 화면 -> session 을 이용하여 로그인 유지
     * @param
     * @param model
     * @return
     */
    @GetMapping("/")
    public String home(Model model) {
        HttpSession session = request.getSession();
        String memberName = (String) session.getAttribute("memberName");
        Long memberId = (Long) session.getAttribute("memberId");
        log.info("session name : {} & session Id : {}", memberName, memberId);
        log.info("home controller");

        if (memberName != null) {
            model.addAttribute("memberName", memberName);
            return "home";
        }

        return "home";
    }


    @PostConstruct
    public void init() {
        // Member
        Member member = new Member("주워니", "dusrbpoiiij@naver.com", LocalDateTime.now());
        memberService.join(member);

        // Category
        Category category1 = new Category(member, "회사", LocalDateTime.now());
        Category category2 = new Category(member, "공부", LocalDateTime.now());
        Category category3 = new Category(member, "운동", LocalDateTime.now());
        categoryService.enrollCategory(category1);
        categoryService.enrollCategory(category2);
        categoryService.enrollCategory(category3);

        // todolist
        LocalDate deadline = LocalDate.now();
        LocalDateTime notification = LocalDateTime.now();
        Long memberId = member.getId();
        Long categoryId1 = category1.getId();
        Long categoryId2 = category2.getId();
        Long categoryId3 = category3.getId();

        todoService.enrollTodo(memberId, categoryId1, "기획서 작성", deadline, notification);
        todoService.enrollTodo(memberId, categoryId2, "영어공부 1시간", deadline, notification);
        todoService.enrollTodo(memberId, categoryId3, "스쿼트 100개", deadline, notification);
    }
}
