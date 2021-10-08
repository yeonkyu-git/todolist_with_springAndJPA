package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.Todo;
import yeonkyu.todolist.domain.TodoStatus;
import yeonkyu.todolist.repository.TodoRepository;
import yeonkyu.todolist.service.CategoryService;
import yeonkyu.todolist.service.MemberService;
import yeonkyu.todolist.service.TodoService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    private final TodoRepository todoRepository;
    private final CategoryService categoryService;
    private final MemberService memberService;
    private final HttpServletRequest request;


    /**
     * 투두리스트 보여주기
     *
     * @param model
     * @return
     */
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

        // TodoDTO에 담아서 프론트로 전달
        for (Todo todo : todoListByMember) {
            TodoDTO todoList = new TodoDTO(
                    todo.getId(),
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

    /**
     * 투두 등록
     *
     * @param todoName
     * @param deadline
     * @param categoryId
     * @param model
     * @return
     */
    @PostMapping("/todolist/create")
    public String createTodo(
            @RequestParam String todoName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
            @RequestParam Long categoryId,
            Model model) {
        // TodoDTO 정의
        List<TodoDTO> todoLists = new ArrayList<>();

        // Session에 저장된 값 불러오기
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("memberId");


        // todo 등록
        Long todoId = todoService.enrollTodo(memberId, categoryId, todoName, deadline);
        List<Todo> todoListByMember = todoService.findTodoListByMember(memberId);

        // TodoDTO에 담아서 프론트로 전달
        for (Todo todo : todoListByMember) {
            TodoDTO todoList = new TodoDTO(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getCreateAt().toLocalDate(),
                    todo.getDeadline(),
                    todo.getNotification(),
                    todo.getComplete());
            todoLists.add(todoList);
        }

        model.addAttribute("todoLists", todoLists);

        return "todolist/todoList :: #sample";
    }

    /**
     * 투두 삭제
     *
     * @param todoId
     * @param model
     * @return
     */
    @PostMapping("/todolist/delete")
    public String deleteTodo(@RequestParam Long todoId, Model model) {
        todoService.deleteTodo(todoId);


        // TodoDTO 정의
        List<TodoDTO> todoLists = new ArrayList<>();

        // Session에 저장된 값 불러오기
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("memberId");


        List<Todo> todoListByMember = todoService.findTodoListByMember(memberId);

        // TodoDTO에 담아서 프론트로 전달
        for (Todo todo : todoListByMember) {
            TodoDTO todoList = new TodoDTO(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getCreateAt().toLocalDate(),
                    todo.getDeadline(),
                    todo.getNotification(),
                    todo.getComplete());
            todoLists.add(todoList);
        }

        model.addAttribute("todoLists", todoLists);

        return "todolist/todoList :: #sample";
    }

    /**
     * 할일 완료 체크
     *
     * @param todoId
     * @param model
     * @return
     */
    @PostMapping("/todolist/complete")
    public String completeTodo(@RequestParam Long todoId, Model model) {
        todoService.updateComplete(todoId);

        // TodoDTO 정의
        List<TodoDTO> todoLists = new ArrayList<>();

        // Session에 저장된 값 불러오기
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("memberId");

        List<Todo> todoListByMember = todoService.findTodoListByMember(memberId);

        // TodoDTO에 담아서 프론트로 전달
        for (Todo todo : todoListByMember) {
            TodoDTO todoList = new TodoDTO(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getCreateAt().toLocalDate(),
                    todo.getDeadline(),
                    todo.getNotification(),
                    todo.getComplete());
            todoLists.add(todoList);
        }

        model.addAttribute("todoLists", todoLists);

        return "todolist/todoList :: #sample";
    }


    @PostMapping("/todolist/update")
    public String updateTodo(@RequestParam Long todoId, @RequestParam String title, Model model) {
        todoService.updateTodoTitle(todoId, title);

        // TodoDTO 정의
        List<TodoDTO> todoLists = new ArrayList<>();

        // Session에 저장된 값 불러오기
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("memberId");

        List<Todo> todoListByMember = todoService.findTodoListByMember(memberId);

        // TodoDTO에 담아서 프론트로 전달
        for (Todo todo : todoListByMember) {
            TodoDTO todoList = new TodoDTO(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getCreateAt().toLocalDate(),
                    todo.getDeadline(),
                    todo.getNotification(),
                    todo.getComplete());
            todoLists.add(todoList);
        }

        model.addAttribute("todoLists", todoLists);

        return "todolist/todoList :: #sample";
    }

    @GetMapping("/todolist/{categoryId}")
    public String viewTodolistByCategory(@PathVariable String categoryId) {
        System.out.println("CategoryId = " + categoryId);
        return "todolist/todoList";
    }
}