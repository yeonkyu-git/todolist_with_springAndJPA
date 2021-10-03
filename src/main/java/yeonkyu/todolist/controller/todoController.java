package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Todo;
import yeonkyu.todolist.service.CategoryService;
import yeonkyu.todolist.service.MemberService;
import yeonkyu.todolist.service.TodoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class todoController {

    private final TodoService todoService;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final HttpServletRequest request;



    @GetMapping("/todolist")
    public String viewTodoList(Model model) {
        // TodoDTO 정의
        List<TodoDTO> todoLists = new ArrayList<>();

        // Session에 저장된 값 불러오기
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("memberId");
        String memberName = (String) session.getAttribute("memberName");

        // 로그인이 안되어 있으면 Home으로 Redirect
        if (memberId == null) {
            return "redirect:/";
        }

        // 로그인된 Member와 관련된 Category 및 Todolist 가져오기
        List<Category> categories = categoryService.findCategories(memberId);
        List<Todo> todoListByMember = todoService.findTodoListByMember(memberId);

        for (Todo todo : todoListByMember) {
            TodoDTO todoList = new TodoDTO(
                    todo.getTitle(),
                    todo.getCreateAt().toLocalDate(),
                    todo.getDeadline(),
                    todo.getNotification(),
                    todo.getComplete());
            todoLists.add(todoList);
        }

        model.addAttribute("memberName", memberName);
        model.addAttribute("categories", categories);
        model.addAttribute("todoLists", todoLists);

        return "todolist/todoList";
    }
}
