package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.MemberRepository;
import yeonkyu.todolist.service.MemberService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final HttpServletRequest request;


    /**
     * 회원가입 화면 들어가기
     * @param
     * @return
     */
    @GetMapping("/members/new")
    public String createForm() {
        return "members/signUp";
    }


    /**
     * 회원가입
     * @param memberDTO
     * @return
     */
    @PostMapping("/members/new")
    public String create(@ModelAttribute MemberDTO memberDTO) {
        Member member = new Member(memberDTO.getName(), memberDTO.getEmail(), LocalDateTime.now());
        memberService.join(member);
        return "redirect:/";
    }

    /**
     * 로그인
     * @param email
     * @param model
     * @param
     * @return
     */
    @PostMapping("/members/signin")
    public String join(@RequestParam String email, Model model) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        HttpSession session = request.getSession();

        for (Member findMember : findMembers) {
            if (email.equals(findMember.getEmail())) {
                model.addAttribute("member", findMember);
                session.setAttribute("memberId", findMember.getId());
                session.setAttribute("memberName", findMember.getName());
                log.info("findMember Name : {}, findMember Id : {}", findMember.getName(), findMember.getId());
                return "redirect:/todolist";
            }
        }
        return "redirect:/";
    }

    /**
     * 로그아웃
     * @param request
     * @return
     */
    @GetMapping("/members/signout")
    public String signOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @ExceptionHandler(IllegalStateException.class)
    public Object nullex(Exception e, Model model) {
        System.err.println(e.getMessage());
        return "redirect:/members/new";
    }

}

