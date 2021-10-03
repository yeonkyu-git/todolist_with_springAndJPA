package yeonkyu.todolist.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Category(Member member, String name, LocalDateTime createAt) {
        this.member = member;
        this.name = name;
        this.createAt = createAt;
    }

    //== 비즈니스 메서드 ==//
    public void changeCategoryName(String name) {
        this.name = name;
    }
}
