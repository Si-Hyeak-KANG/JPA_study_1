package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // Jpa의 어떤 모든 데이터 변경이나 로직들은 가급적이면 transactional 안에서 모두 실행되어야함.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional // readOnly = false
    public Long join(Member member) {

        //회원 가입시, 중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId(); // entity를 생성할 때 이미 값이 존재
    }

    // multi thread 상황을 고려하여 실무에서는 한번더 방어선 구축 (db에서 unique 제약 조건으로 잡아줌)
    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단권 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
