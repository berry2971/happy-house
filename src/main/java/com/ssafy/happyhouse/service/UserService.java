package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.config.security.JwtTokenProvider;
import com.ssafy.happyhouse.domain.entity.User;
import com.ssafy.happyhouse.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

@Service
@Transactional
public class UserService {

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(
            UserMapper userMapper,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder
    ) {
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(String id) throws Exception {
        return userMapper.findById(id);
    }

    public User registerUser(User user) throws Exception {
        user.setPw(passwordEncoder.encode(user.getPw()));
        userMapper.save(user);
        return user;
    }

    public User modifyUser(User user) throws Exception {
        userMapper.modifyUser(user);
        return user;
    }

    public String login(String id, String pw) throws Exception {
        User findUser = userMapper.findById(id);
        if (findUser == null || passwordEncoder.matches(pw, findUser.getPw())) {
            throw new AuthenticationException("아이디가 존재하지 않거나 비밀번호가 잘못되었습니다.");
        }
        String token = jwtTokenProvider.createToken(id);
        return token;
    }

}
