package com.ssafy.happyhouse.config.security;

import com.ssafy.happyhouse.domain.entity.User;
import com.ssafy.happyhouse.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public CustomUserDetailsService(
            UserMapper userMapper
    ) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            User user = userMapper.findById(id);
            return new CustomUserDetails(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("없습니다...");
        }
    }

}
