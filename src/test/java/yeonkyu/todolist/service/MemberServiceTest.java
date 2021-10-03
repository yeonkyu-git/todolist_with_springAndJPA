package yeonkyu.todolist.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member("Kim", "dusrbpoiiij@naver.com", LocalDateTime.now());

        //when
        Long memberId = memberService.join(member);
        Member findMember = memberService.findOne(memberId);

        //then
        assertThat(member).isEqualTo(findMember);
    }


    @Test(expected = IllegalStateException.class)
    public void 중복회원검사() throws Exception {
        //given
        Member member1 = new Member("Kim", "dusrbpoiiij@naver.com", LocalDateTime.now());
        Member member2 = new Member("Lee", "dusrbpoiiij@naver.com", LocalDateTime.now());

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생하여야 한다.");
    }


    @Test
    public void 회원찾기_이메일() throws Exception {
        //given
        Member member = new Member("Kim", "dusrbpoiiij@naver.com", LocalDateTime.now());
        Long memberId = memberService.join(member);

        //when
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());

        //then
        assertThat(findMembers.size()).isEqualTo(1);
    }
}