package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class MemberRepository {

    // 스프링 부트가 EntityManager 주입
    @PersistenceContext
    private EntityManager em;

    // entity manager factory를 직접 주입 받는 방법 // 하지만 잘 안씀
    /*@PersistenceUnit
    private EntityManagerFactory emf;*/

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // find(type, pk(parameter))
    }

    public List<Member> findAll() {

        return em.createQuery("select m from Member m", Member.class).getResultList();

        // sql은 테이블 대상 query, Jpql은 entity 객체를 대상으로 query
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
