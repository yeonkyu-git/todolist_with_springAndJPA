package yeonkyu.todolist.controller;


import lombok.Data;
import lombok.Getter;
import yeonkyu.todolist.domain.NotificationStatus;
import yeonkyu.todolist.domain.TodoStatus;

import java.time.LocalDate;

@Data
@Getter
public class TodoDTO {

    private Long id;
    private String todoName;
    private LocalDate createAt;
    private LocalDate deadline;
    private NotificationStatus notificationStatus;
    private TodoStatus complete;

    public TodoDTO(Long id, String todoName, LocalDate createAt, LocalDate deadline, NotificationStatus notificationSatus, TodoStatus complete) {
        this.id = id;
        this.todoName = todoName;
        this.createAt = createAt;
        this.deadline = deadline;
        this.notificationStatus = notificationSatus;
        this.complete = complete;
    }
}
