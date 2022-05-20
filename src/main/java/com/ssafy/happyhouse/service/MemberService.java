package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.config.security.JwtTokenProvider;
import com.ssafy.happyhouse.domain.entity.Member;
import com.ssafy.happyhouse.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

@Service
@Transactional
public class MemberService {

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(
            UserMapper userMapper,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder
    ) {
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public Member getMember(String id) throws Exception {
        return userMapper.findById(id);
    }

    public Member registerUser(Member member) throws Exception {
        Member findMember = getMember(member.getId());
        if (findMember != null) {
            throw new DuplicateKeyException("이미 존재하는 아이디입니다.");
        } else {
            userMapper.save(member);
            return member;
        }
    }

    public Member modifyMember(Member member) throws Exception {
        userMapper.modifyUser(member);
        return member;
    }

    public String login(String id, String pw) throws Exception {
        Member findMember = userMapper.findById(id);
        if (findMember == null || passwordEncoder.matches(pw, findMember.getPw())) {
            throw new AuthenticationException("아이디가 존재하지 않거나 비밀번호가 잘못되었습니다.");
        }
        String token = jwtTokenProvider.createToken(id);
        return token;
    }

}
