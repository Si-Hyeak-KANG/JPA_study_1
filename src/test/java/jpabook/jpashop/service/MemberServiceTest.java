package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class) // junit을 실행할 때 spring과 엮어서 실행, juint5 이후에는 필요없음
@SpringBootTest // Autowired를 적용시켜줌
@Transactional // db 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    //@Rollback(false) -> @Transactional이 롤백해버림 -false하면 롤백 x, db에 반영
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); // 롤백 전에 쿼리를 보여줌 -> insert문을 볼 수 있음.
        assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생한다.

/*        try {
            memberService.join(member2); // 예외가 발생한다!!

        } catch(IllegalStateException e) {
            return;
        }
        -> test 어노테이션에 expected로 추가해서 try-catch 대신 사용할 수 있다.
*/
        //then
        fail("예외가 발생해야 한다.");
    }
}