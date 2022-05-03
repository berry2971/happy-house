package com.ssafy.happyhousemay5.controller;

import com.ssafy.happyhousemay5.domain.dto.UserFindPasswordDto;
import com.ssafy.happyhousemay5.domain.dto.UserLoginDto;
import com.ssafy.happyhousemay5.domain.entity.User;
import com.ssafy.happyhousemay5.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원 조회", notes = "id를 통해 회원 정보를 조회")
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) throws Exception {
        return userService.getUser(id);
    }

    @ApiOperation(value = "회원 등록", notes = "User 객체를 통해 신규 회원을 등록")
    @PostMapping("/")
    public User registerUser(@RequestBody User user) throws Exception {
        return userService.registerUser(user);
    }

    @ApiOperation(value = "회원 수정", notes = "id와 User 객체를 통해 기존 회원 정보를 수정")
    @PutMapping("/{id}")
    public User modifyUser(@PathVariable String id, @RequestBody User user) throws Exception {
        if (!checkIdIsEqualToTarget(id, user)) {
            throw new IllegalArgumentException("요청 id와 변경 id가 다릅니다");
        }
        return userService.modifyUser(id, user);
    }

    @ApiOperation(value = "로그인", notes = "id와 pw를 통해 로그인")
    @PostMapping("/login")
    public User login(@RequestBody UserLoginDto userLoginDto,
                      HttpServletRequest request
    ) throws Exception {
        User user = userService.getUser(userLoginDto.getId());

        if (!checkUserExists(user)) {
            throw new AuthenticationException("존재하지 않는 아이디");
        } else if (!checkIdMatchesPw(user, userLoginDto.getPw())) {
            throw new AuthenticationException("비밀번호 일치하지 않음");
        } else {
            saveLoginInfoToSession(request.getSession(), userLoginDto.getId());
            return user;
        }
    }

    @ApiOperation(value = "로그인 정보 조회", notes = "현재 세션에 로그인되어 있는 계정 정보를 조회")
    @GetMapping("/login/current")
    public User getCurrentUser(HttpServletRequest request) throws Exception {
        String id = retrieveLoginInfoFromSession(request.getSession());
        if (id == null) {
            throw new AccessDeniedException("로그인되어 있지 않음");
        }
        User user = userService.getUser(id);
        if (!checkUserExists(user)) {
            throw new AuthenticationException("존재하지 않는 아이디");
        } else {
            return user;
        }
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "id, tel, name을 통해 비밀번호 찾기")
    @PostMapping("/find-password")
    public String findPassword(
            @RequestBody UserFindPasswordDto userFindPasswordDto
    ) throws Exception {
        String id = userFindPasswordDto.getId();
        String name = userFindPasswordDto.getName();
        String tel = userFindPasswordDto.getTel();

        User user = userService.getUser(id);
        if (!checkUserExists(user)) {
            throw new AuthenticationException("존재하지 않는 아이디");
        } if (!checkNameAndTelEqualsTarget(user, name, tel)) {
            throw new AccessDeniedException("이름 또는 전화번호가 일치하지 않음");
        } else {
            return user.getPw();
        }
    }

    @ApiOperation(value = "로그아웃", notes = "현재 세션에 로그인된 계정을 로그아웃")
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return "success";
    }

    private boolean checkIdIsEqualToTarget(String id, User user) {
        return id.equals(user.getId());
    }

    private boolean checkUserExists(User user) {
        return user != null;
    }

    private boolean checkIdMatchesPw(User user, String pw) throws Exception {
        return user != null && pw.equals(user.getPw());
    }

    private void saveLoginInfoToSession(HttpSession httpSession, String id) {
        httpSession.setAttribute("login-id", id);
    }

    private String retrieveLoginInfoFromSession(HttpSession httpSession) {
        return (String)httpSession.getAttribute("login-id");
    }

    private boolean checkNameAndTelEqualsTarget(User user, String name, String tel) {
        return user.getName().equals(name) && user.getTel().equals(tel);
    }

}
