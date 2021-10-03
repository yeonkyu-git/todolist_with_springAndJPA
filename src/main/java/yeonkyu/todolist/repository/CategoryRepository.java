package yeonkyu.todolist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Todo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
public class CategoryRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 카테고리 등록
     * @param category
     */
    public void save(Category category) {
        em.persist(category);
    }

    /**
     * Category ID로 Category 찾기
     * @param id
     * @return
     */
    public Category findOne(Long id) {
        return em.find(Category.class, id);
    }

    /**
     * Category Name으로 찾기
     * @param name
     * @return
     */
    public List<Category> findByName(String name) {
        return em.createQuery("select c from Category c where c.name = :name", Category.class)
                .setParameter("name", name)
                .getResultList();
    }


    /**
     * 모든 카테고리 찾기
     * @return
     */
    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    /**
     * Member와 연관된 Category 찾기
     * @param memberId
     * @return
     */
    public List<Category> findByMember(Long memberId) {
        return em.createQuery("select c from Category c join c.member m where m.id = :memberId", Category.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    /**
     * 카테고리 삭제
     * @param category
     */
    public void delete(Category category) {
        em.remove(category);
    }

}
