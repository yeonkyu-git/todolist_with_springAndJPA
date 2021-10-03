package yeonkyu.todolist.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TodoCategory {

    @Id
    @GeneratedValue
    @Column(name = "TODOCATEGORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TODO_ID")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;


    //== 생성 메소드 ==//
    public static TodoCategory createTodoCategory(Category category) {
        TodoCategory todoCategory = new TodoCategory();
        todoCategory.updateCategory(category);
        return todoCategory;
    }

    //== 비즈니스 메소드 ==//
    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateTodo(Todo todo) {
        this.todo = todo;
    }


}
