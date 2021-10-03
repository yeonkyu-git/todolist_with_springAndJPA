package yeonkyu.todolist.repository;

import org.springframework.stereotype.Repository;
import yeonkyu.todolist.domain.Todo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TodoRepository {

    @PersistenceContext
    private EntityManager em;


    /**
     * 할일 등록
     * @param todo
     */
    public void save(Todo todo) {
        em.persist(todo);
    }

    /**
     * Id로 투두 찾기
     * @param id
     * @return
     */
    public Todo findOne(Long id) {
        return em.find(Todo.class, id);
    }

    /**
     * 모든 할일 찾기
     * @return
     */
    public List<Todo> findAll() {
        return em.createQuery("select t from Todo t", Todo.class)
                .getResultList();
    }

    /**
     * MemberId 로 조회한 투두 리스트
     * @param memberId
     * @return
     */
    public List<Todo> findByMember(Long memberId) {
        return em.createQuery("select t from Todo t inner join t.member m where m.id = :memberId", Todo.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    /**
     * 할일 삭제
     * @param todo
     */
    public void delete(Todo todo) {
        em.remove(todo);
    }
}
