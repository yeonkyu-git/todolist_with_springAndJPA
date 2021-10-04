package yeonkyu.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.Todo;
import yeonkyu.todolist.domain.TodoCategory;
import yeonkyu.todolist.repository.CategoryRepository;
import yeonkyu.todolist.repository.MemberRepository;
import yeonkyu.todolist.repository.TodoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 투두 생성
     * @param memberId
     * @param categoryId
     * @param title
     * @param
     * @param deadline
     * @param notification
     * @return
     */
    @Transactional
    public Long enrollTodo(Long memberId, Long categoryId, String title, LocalDate deadline) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Category category = categoryRepository.findOne(categoryId);

        // TodoCategory 생성
        TodoCategory todoCategory = TodoCategory.createTodoCategory(category);

        // Todo 생성
        Todo todo = Todo.createTodo(title, deadline, member, todoCategory);

        // Todo 저장
        todoRepository.save(todo);

        return todo.getId();
    }

    /**
     * Member에 대한 TodoList 찾기
     * @param memberId
     * @return
     */
    public List<Todo> findTodoListByMember(Long memberId) {
        return todoRepository.findByMember(memberId);
    }

    /**
     * TodoList 삭제
     * @param todoId
     */
    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findOne(todoId);
        todoRepository.delete(todo);
    }

    /**
     * 할일 Title 변경
     * @param todoId
     * @param title
     */
    @Transactional
    public void updateTodoTitle(Long todoId, String title) {
        Todo todo = todoRepository.findOne(todoId);
        todo.changeTitle(title);
    }

    /**
     * 할일 완료 체크 및 체크 해제
     * @param todoId
     */
    @Transactional
    public void updateComplete(Long todoId) {
        Todo todo = todoRepository.findOne(todoId);
        todo.changeComplete();
    }

    /**
     * 할일 마감 시한 설정
     * @param todoId
     * @param deadline
     */
    @Transactional
    public void updateTodoDeadline(Long todoId, LocalDate deadline) {
        Todo todo = todoRepository.findOne(todoId);
        todo.changeDeadline(deadline);
    }

    /**
     * 할일 마감 시한 삭제
     * @param todoId
     */
    @Transactional
    public void deleteTodoDeadline(Long todoId) {
        Todo todo = todoRepository.findOne(todoId);
        todo.deleteDeadline();
    }
}
