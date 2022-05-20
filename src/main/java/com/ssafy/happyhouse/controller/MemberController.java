package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.domain.dto.MemberFindPasswordDto;
import com.ssafy.happyhouse.domain.dto.MemberLoginDto;
import com.ssafy.happyhouse.domain.entity.Member;
import com.ssafy.happyhouse.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;

@CrossOrigin("*")
@RestController
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "회원 조회", notes = "id를 통해 회원 정보를 조회")
    @GetMapping("/{id}")
    public Member getMember(@PathVariable String id) throws Exception {
        return memberService.getMember(id);
    }

    @ApiOperation(value = "회원 등록", notes = "User 객체를 통해 신규 회원을 등록")
    @PostMapping("/")
    public Member registerMember(@RequestBody Member member) throws Exception {
        return memberService.registerUser(member);
    }

    @ApiOperation(value = "회원 수정", notes = "id와 User 객체를 통해 기존 회원 정보를 수정")
    @PutMapping("/{id}")
    public Member modifyMember(@PathVariable String id, @RequestBody Member member) throws Exception {
        if (!checkIdIsEqualToTarget(id, member)) {
            throw new IllegalArgumentException("요청 id와 변경 id가 다릅니다");
        }
        return memberService.modifyMember(member);
    }

    @ApiOperation(value = "로그인", notes = "id와 pw를 통해 로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginDto memberLoginDto) throws Exception {
        String token = memberService.login(memberLoginDto.getId(), memberLoginDto.getPw());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @ApiOperation(value = "로그인 정보 조회", notes = "현재 세션에 로그인되어 있는 계정 정보를 조회")
    @GetMapping("/login/current")
    public Member getCurrentMember(HttpServletRequest request) throws Exception {
        String id = retrieveLoginInfoFromSession(request.getSession());
        if (id == null) {
            throw new AccessDeniedException("로그인되어 있지 않음");
        }
        Member member = memberService.getMember(id);
        if (!checkMemberExists(member)) {
            throw new AuthenticationException("존재하지 않는 아이디");
        } else {
            return member;
        }
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "id, tel, name을 통해 비밀번호 찾기")
    @PostMapping("/find-password")
    public String findPassword(
            @RequestBody MemberFindPasswordDto memberFindPasswordDto
    ) throws Exception {
        String id = memberFindPasswordDto.getId();
        String name = memberFindPasswordDto.getName();
        String tel = memberFindPasswordDto.getTel();

        Member member = memberService.getMember(id);
        if (!checkMemberExists(member)) {
            throw new AuthenticationException("존재하지 않는 아이디");
        } if (!checkNameAndTelEqualsTarget(member, name, tel)) {
            throw new AccessDeniedException("이름 또는 전화번호가 일치하지 않음");
        } else {
            return member.getPw();
        }
    }

    @ApiOperation(value = "로그아웃", notes = "현재 세션에 로그인된 계정을 로그아웃")
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return "success";
    }

    private boolean checkIdIsEqualToTarget(String id, Member member) {
        return id.equals(member.getId());
    }

    private boolean checkMemberExists(Member member) {
        return member != null;
    }

    private boolean checkIdMatchesPw(Member member, String pw) throws Exception {
        return member != null && pw.equals(member.getPw());
    }

    private void saveLoginInfoToSession(HttpSession httpSession, String id) {
        httpSession.setAttribute("login-id", id);
    }

    private String retrieveLoginInfoFromSession(HttpSession httpSession) {
        return (String)httpSession.getAttribute("login-id");
    }

    private boolean checkNameAndTelEqualsTarget(Member member, String name, String tel) {
        return member.getName().equals(name) && member.getTel().equals(tel);
    }

}
