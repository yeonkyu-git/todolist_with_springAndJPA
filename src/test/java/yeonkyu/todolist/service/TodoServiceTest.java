package yeonkyu.todolist.service;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.*;
import yeonkyu.todolist.repository.MemberRepository;
import yeonkyu.todolist.repository.TodoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TodoServiceTest {

    @Autowired
    TodoService todoService;
    @Autowired
    MemberService memberService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    TodoRepository todoRepository;
    @Autowired
    MemberRepository memberRepository;

    Member member1 = new Member("joowon", "skyblue9221@gmail.com", LocalDateTime.now());
    Member member2 = new Member("yeonkyu", "dusrbpoiiij@naver.com", LocalDateTime.now());
    Category category1 = new Category(member1, "work", LocalDateTime.now());
    Category category2 = new Category(member1, "english", LocalDateTime.now());

    @Before
    public void beforeEachMethod() {
        memberService.join(member1);
        memberService.join(member2);

        categoryService.enrollCategory(category1);
        categoryService.enrollCategory(category2);
    }

    @Test
    public void 할일등록() throws Exception {
        //given
        String title = "영어공부 Chapter 1 끝내기";
        LocalDate deadline = LocalDate.now();
        LocalDateTime notification = LocalDateTime.now();
        Long memberId = member1.getId();
        Long categoryId = category1.getId();

        //when
        Long todoId = todoService.enrollTodo(memberId, categoryId, title, deadline, notification);

        //then
        Todo findTodo = todoRepository.findOne(todoId);
        List<TodoCategory> todoCategories = findTodo.getTodoCategories();

        assertThat(findTodo.getTitle()).isEqualTo("영어공부 Chapter 1 끝내기");
        for (TodoCategory todoCategory : todoCategories) {
            assertThat(todoCategory.getCategory().getName()).isEqualTo("work");
        }
    }


    @Test
    public void 멤버를_통해_투두_찾기() throws Exception {
        //given
        String title = "영어공부 Chapter 1 끝내기";
        String description = "백종원 책으로 공부 하자";
        LocalDate deadline = LocalDate.now();
        LocalDateTime notification = LocalDateTime.now();
        Long memberId = member1.getId();
        Long memberId2 = member2.getId();
        Long categoryId = category1.getId();

        //when
        Long todoId = todoService.enrollTodo(memberId, categoryId, title, deadline, notification);
        Long todoId2 = todoService.enrollTodo(memberId2, categoryId, title, deadline, notification);

        //then
        Todo findTodo = todoRepository.findOne(todoId);
        List<Todo> findTodoListByMember = todoService.findTodoListByMember(memberId);
        List<Todo> todoAll = todoRepository.findAll();
        assertThat(findTodoListByMember.size()).isEqualTo(1);
        assertThat(todoAll.size()).isEqualTo(2);

        for (Todo todo : findTodoListByMember) {
            assertThat(todo.getTitle()).isEqualTo("영어공부 Chapter 1 끝내기");
        }
    }


    @Test
    public void 투두리스트_삭제() throws Exception {
        //given
        String title = "영어공부 Chapter 1 끝내기";
        String description = "백종원 책으로 공부 하자";
        LocalDate deadline = LocalDate.now();
        LocalDateTime notification = LocalDateTime.now();
        Long memberId = member1.getId();
        Long categoryId = category1.getId();

        //when
        Long todoId = todoService.enrollTodo(memberId, categoryId, title, deadline, notification);
        todoService.deleteTodo(todoId);

        //then
        Todo findTodo = todoRepository.findOne(todoId);
        assertThat(findTodo).isNull();
    }

    @Test
    public void 할일_제목_변경() throws Exception {
        //given
        String title = "영어공부 Chapter 1 끝내기";
        String description = "백종원 책으로 공부 하자";
        LocalDate deadline = LocalDate.now();
        LocalDateTime notification = LocalDateTime.now();
        Long memberId = member1.getId();
        Long categoryId = category1.getId();

        //when
        Long todoId = todoService.enrollTodo(memberId, categoryId, title, deadline, notification);
        todoService.updateTodoTitle(todoId, "할일변경!");
        Todo findTodo = todoRepository.findOne(todoId);

        //then
        assertThat(findTodo.getTitle()).isEqualTo("할일변경!");
    }

    @Test
    public void 할일완료체크() throws Exception {
        //given
        String title = "영어공부 Chapter 1 끝내기";
        String description = "백종원 책으로 공부 하자";
        LocalDate deadline = LocalDate.now();
        LocalDateTime notification = LocalDateTime.now();
        Long memberId = member1.getId();
        Long categoryId = category1.getId();

        //when
        Long todoId = todoService.enrollTodo(memberId, categoryId, title, deadline, notification);
        todoService.updateComplete(todoId);
        Todo findTodo = todoRepository.findOne(todoId);

        //then
        assertThat(findTodo.getComplete()).isEqualTo(TodoStatus.COMPLETE);

    }

}