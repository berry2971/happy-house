package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.config.security.JwtTokenProvider;
import com.ssafy.happyhouse.domain.entity.User;
import com.ssafy.happyhouse.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(
            UserMapper userMapper,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(id, pw)
        );
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String token = jwtTokenProvider.createToken(userDetails.getUsername());
        return token;
    }

}