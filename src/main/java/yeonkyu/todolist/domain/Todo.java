package yeonkyu.todolist.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue
    @Column(name = "TODO_ID")
    private Long id;

    private String title;
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notification;

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    private TodoStatus complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<TodoCategory> todoCategories = new ArrayList<>();

    //== 생성 메소드==//
    public static Todo createTodo (String title, LocalDate deadline, Member member, TodoCategory todoCategory) {
        Todo todo = new Todo();
        todo.changeTitle(title);
        todo.changeDeadline(deadline);
        todo.updateMember(member);
        todo.updateTodoCategory(todoCategory);
        todo.updateCreateAt();
        todo.complete = TodoStatus.DOING;
        todo.notification = todo.changeNotification();

        return todo;
    }


    //== 비즈니스 메서드 ==//
    public void changeTitle(String title) {
        this.title = title;
    }


    public void changeComplete() {
        if (this.complete == TodoStatus.DOING) {
            this.complete = TodoStatus.COMPLETE;
        } else{
            this.complete = TodoStatus.DOING;
        }
    }

    public void changeDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public NotificationStatus changeNotification() {
        LocalDate now = LocalDate.now();
        long fromNowToDeadLine = ChronoUnit.DAYS.between(now, this.deadline);

        if (fromNowToDeadLine < 1) {
            return NotificationStatus.OK;
        } else {
            return NotificationStatus.NO;
        }
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateTodoCategory(TodoCategory todoCategory) {
        this.todoCategories.add(todoCategory);
        todoCategory.updateTodo(this);
    }

    public void updateCreateAt() {
        this.createAt = LocalDateTime.now();
    }

    // 할일 마감 시한을 삭제하는 건 null로 하면 될까??
    public void deleteDeadline() {
        this.deadline = null;
    }

}
