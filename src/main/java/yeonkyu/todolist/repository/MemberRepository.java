package yeonkyu.todolist.repository;

import org.springframework.stereotype.Repository;
import yeonkyu.todolist.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 멤버 등록
     * @param member
     */
    public void save(Member member) {
        em.persist(member);
    }

    // == 나중을 위한 추가 기능 == //

    /**
     * 특정 멤버 찾기 (by Id)
     * @param id
     * @return
     */
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    /**
     * 모든 멤버 찾기
     * @return
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /**
     * 특정 멤버 찾기 (by Email)
     * @param email
     * @return
     */
    public List<Member> findByEmail(String email) {
        List<Member> findMembers = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        return findMembers;
    }
}
